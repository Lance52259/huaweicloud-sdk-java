package com.huaweicloud.sdk.test;

import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.http.HttpConfig;
import com.huaweicloud.sdk.core.region.Region;
import com.huaweicloud.sdk.test.vpc.*;
import com.huaweicloud.sdk.vpc.v2.VpcClient;
import com.huaweicloud.sdk.vpc.v2.model.*;

import org.apache.commons.lang3.RandomStringUtils;

public class Application {
    public static void main(String[] args) throws InterruptedException {

        VpcCreate vpcCreate = new VpcCreate();
        VpcDelete vpcDelete = new VpcDelete();

        String ak         = "your ak";
        String sk         = "your sk";
        String regionName = "cn-north-4";

        String random       = RandomStringUtils.randomAlphabetic(5);
        String vpcName      = "vpc-demo-"+random;
        String subnetName   = "subnet-demo-"+random;
        String secGroupName = "SecGroup-demo-"+random;

        String vpcCidr    = "192.168.0.0/16";
        String subnetCidr = "192.168.0.0/24";
        String gatewayIp  = "192.168.0.1";

        String ipVersion4 = "IPv4";
        String ipVersion6 = "IPv6";
        String ingressDir = "ingress";
        String egressDir  = "egress";

        HttpConfig config = HttpConfig.getDefaultHttpConfig();
        config.withIgnoreSSLVerification(true);

        BasicCredentials auth = new BasicCredentials()
                .withAk(ak)
                .withSk(sk);

        Region vpcRegion = new Region(regionName,"https://vpc.cn-north-4.myhuaweicloud.com");

        VpcClient vpcClient = VpcClient.newBuilder()
                .withHttpConfig(config)
                .withCredential(auth)
                .withRegion(vpcRegion)
                .build();

        //Create
        Vpc vpc = vpcCreate.createVpc(vpcClient, vpcName, vpcCidr);
        Subnet subnet = vpcCreate.createSubnet(vpcClient, subnetName, subnetCidr, gatewayIp, vpc.getId());
        SecurityGroup securityGroup = vpcCreate.createSecurityGroup(vpcClient, vpc.getId(), secGroupName);
        SecurityGroupRule securityGroupRule1 = vpcCreate.createRuleWithRemoteIpPrefix(vpcClient, securityGroup.getId(), ipVersion4, ingressDir, 445, 445, "tcp", "0.0.0.0/0");
        SecurityGroupRule securityGroupRule2 = vpcCreate.createRuleWithRemoteIpPrefix(vpcClient, securityGroup.getId(), ipVersion4, egressDir, 0, 0, "icmp", "0.0.0.0/0");

        //Delete;
        vpcDelete.deleteSubnet(vpcClient, vpc.getId(), subnet.getId());
        vpcDelete.deleteVpc(vpcClient, vpc.getId());
        vpcDelete.deleteSecurityGroup(vpcClient, securityGroup.getVpcId(), securityGroup.getId());
    }
}
