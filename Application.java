package com.huaweicloud.sdk.test;

import com.huaweicloud.sdk.as.v1.AsClient;
import com.huaweicloud.sdk.as.v1.model.*;
import com.huaweicloud.sdk.ces.v1.CesClient;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.http.HttpConfig;
import com.huaweicloud.sdk.core.region.Region;
import com.huaweicloud.sdk.test.as.AsCreate;
import com.huaweicloud.sdk.test.as.AsDelete;
import com.huaweicloud.sdk.test.as.AsRead;
import com.huaweicloud.sdk.test.as.AsUpdate;
import com.huaweicloud.sdk.test.vpc.*;
import com.huaweicloud.sdk.vpc.v2.VpcClient;
import com.huaweicloud.sdk.vpc.v2.model.*;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) throws InterruptedException {

        //VPC
        VpcCreate vpcCreate = new VpcCreate();
        VpcDelete vpcDelete = new VpcDelete();
        //Auto Scaling
        AsCreate asCreate = new AsCreate();
        AsRead   asRead   = new AsRead();
        AsUpdate asUpdate = new AsUpdate();
        AsDelete asDelete = new AsDelete();


        String ak         = "IW7HCVHGRKB7ZNCGEKSL";
        String sk         = "nEWzDbZRVGtE1ZpQeRD39JbUsW393qXFTBboXavJ";
        String regionName = "cn-north-4";

        String random       = RandomStringUtils.randomAlphabetic(5);
        String vpcName      = "vpc-demo-"+random;
        String subnetName   = "subnet-demo-"+random;
        String secGroupName = "SecGroup-demo-"+random;
        String asConfigName = "scalingConfig-demo-"+random;
        String asGroupName  = "AutoScaling-demo-"+random;
        String policyName   = "policy-demo-"+random;
        String alarmName    = "alarm-demo-"+random;

        String vpcCidr    = "192.168.0.0/16";
        String subnetCidr = "192.168.0.0/24";
        String gatewayIp  = "192.168.0.1";

        String ipVersion4 = "IPv4";
        String ipVersion6 = "IPv6";
        String ingressDir = "ingress";
        String egressDir  = "egress";

        //Parameter of Auto Scaling Group
        int desireNum        = 2;
        int maxNum           = 5;
        int minNum           = 1;
        int coolDownTime     = 300;
        int auditTime        = 180;
        int auditGracePeriod = 600;
        String listenerId       = null;
        String auditMethod      = "NOVA_AUDIT";  // ELB_AUDIT
        String terminaatePolicy = "OLD_CONFIG_OLD_INSTANCE";
        boolean deletePublicIp  = true;
        String azPriorityPolicy = "EQUILIBRIUM_DISTRIBUTE";
        List<LbaasListeners> lbaasListenersList = new ArrayList<>();
        List<String> availabaleZonesList = new ArrayList<>();

        String isDelete = "yes";
            //Instance config - Disk
        String[][] diskConfig = {{"SYS","SAS","40"}, {"DATA","SAS","10"}};
        String bindwidthType  = "5_bgp";
        int bindwidthSize     = 5;
        String flavorId       = "c6.xlarge.4";
        String imageId        = "638a15ac-bc2b-4f87-a896-047a271caf65";
        String keyPairName    = "KeyPair-1650";

        //
        int period = 300;
        int count = 3;
        int suppressDuration = 900;
        double threshold_1 = 75.0;
        double threshold_2 = 20.0;
        String filter_1 = "max";
        String filter_2 = "average";
        String comparisonOperator_1 = ">=";
        String comparisonOperator_2 = "<=";
        String unit = "%";

        String operation_1 = "add";
        String operation_2 = "remove";
        int instanceNum_1 = 1;
        int instanceNum_2 = 1;

        String namespace  = "SYS.AS";
        String metricName = "cpu_util";

        HttpConfig config = HttpConfig.getDefaultHttpConfig();
        config.withIgnoreSSLVerification(true);

        BasicCredentials auth = new BasicCredentials()
                .withAk(ak)
                .withSk(sk);

        Region vpcRegion = new Region(regionName,"https://vpc.cn-north-4.myhuaweicloud.com");
        Region asRegion  = new Region(regionName,"https://as.cn-north-4.myhuaweicloud.com");
        Region cesRegion = new Region(regionName,"https://ces.cn-north-4.myhuaweicloud.com");

        VpcClient vpcClient = VpcClient.newBuilder()
                .withHttpConfig(config)
                .withCredential(auth)
                .withRegion(vpcRegion)
                .build();

        //Auto Scaling
        AsClient asClient = AsClient.newBuilder()
                .withHttpConfig(config)
                .withCredential(auth)
                .withRegion(asRegion)
                .build();

        CesClient cesClient = CesClient.newBuilder()
                .withHttpConfig(config)
                .withCredential(auth)
                .withRegion(cesRegion)
                .build();

        //Create VPC, Subnet, Security Group and Security Group Rule
        Vpc vpc = vpcCreate.createVpc(vpcClient, vpcName, vpcCidr);
        Subnet subnet = vpcCreate.createSubnet(vpcClient, subnetName, subnetCidr, gatewayIp, vpc.getId());
        SecurityGroup securityGroup = vpcCreate.createSecurityGroup(vpcClient, vpc.getId(), secGroupName);
        SecurityGroupRule securityGroupRule1 = vpcCreate.createRuleWithRemoteIpPrefix(vpcClient, securityGroup.getId(), ipVersion4, ingressDir, 445, 445, "tcp", "0.0.0.0/0");
        SecurityGroupRule securityGroupRule2 = vpcCreate.createRuleWithRemoteIpPrefix(vpcClient, securityGroup.getId(), ipVersion4, egressDir, 0, 0, "icmp", "0.0.0.0/0");
        //Create Auto Scaling Group, Auto Scaling Configuration
        InstanceConfig instanceConfig = asRead.getScalingInstanceConfig(securityGroup.getId(), flavorId, imageId, keyPairName, diskConfig, bindwidthSize, bindwidthType);
        String asConfigId = asCreate.createScalingConfiguration(asClient, instanceConfig, asConfigName);

        String asGroupId = asCreate.createAutoScalingGroup(asClient, asGroupName, asConfigId, desireNum, minNum, maxNum, coolDownTime,
                listenerId, lbaasListenersList, availabaleZonesList,
                new ArrayList<>(Arrays.asList(new SecurityGroups().withId(securityGroup.getId()))),
                vpc.getId(), new ArrayList<>(Arrays.asList(new Networks().withId(subnet.getId()))),
                auditMethod, auditTime, auditGracePeriod, terminaatePolicy, deletePublicIp, azPriorityPolicy);
        //Policy and Alarm
        String alarmId_1 = asCreate.createAlarm(cesClient, alarmName, metricName, namespace, asGroupId, period, filter_1, comparisonOperator_1, threshold_1, unit, count);
        String alarmId_2 = asCreate.createAlarm(cesClient, alarmName, metricName, namespace, asGroupId, period, filter_2, comparisonOperator_2, threshold_2, unit, count);
        String policyId_1 = asCreate.createScalingPolicyWithAlarm(asClient, policyName+"-1", asGroupId, operation_1, instanceNum_1, alarmId_1, coolDownTime);
        String policyId_2 = asCreate.createScalingPolicyWithAlarm(asClient, policyName+"-2", asGroupId, operation_2, instanceNum_2, alarmId_2, coolDownTime);
        asUpdate.updateScalingGroupStatus(asClient, asGroupId, "resume");

        Thread.sleep(180000);

        //Delete;
        asDelete.deleteScalingGroup(asClient, asGroupId, isDelete);
        asDelete.deleteScalingConfig(asClient, asConfigId);

        vpcDelete.deleteSecurityGroup(vpcClient, securityGroup.getVpcId(), securityGroup.getId());
        vpcDelete.deleteSubnet(vpcClient, vpc.getId(), subnet.getId());
        vpcDelete.deleteVpc(vpcClient, vpc.getId());
//
//        asDelete.deleteScalingPolicy(asClient, policyId_1);
//        asDelete.deleteScalingPolicy(asClient, policyId_2);
//        asDelete.deleteAlarm(cesClient, alarmId_1);
//        asDelete.deleteAlarm(cesClient, alarmId_2);
    }
}
