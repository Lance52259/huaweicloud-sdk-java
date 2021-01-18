package com.huaweicloud.sdk.test.as;

import com.huaweicloud.sdk.as.v1.AsClient;
import com.huaweicloud.sdk.as.v1.model.*;
import com.huaweicloud.sdk.ces.v1.CesClient;
import com.huaweicloud.sdk.ces.v1.model.*;
import com.huaweicloud.sdk.core.exception.ClientRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class AsRead {
    private static final Logger logger = LoggerFactory.getLogger(AsRead.class);

    public List<ScalingGroups> getScalingGroupsListByName(AsClient client, String asGroupName) {
        ListScalingGroupsResponse listScalingGroupsResponse = new ListScalingGroupsResponse();
        try {
            listScalingGroupsResponse = client.listScalingGroups(new ListScalingGroupsRequest()
                    .withScalingGroupName(asGroupName));
        } catch (ClientRequestException e) {
            logger.error("[Scaling Group]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Scaling Group]RequestId: " + e.getRequestId());
            logger.error("[Scaling Group]ErrorCode: " + e.getErrorCode());
            logger.error("[Scaling Group]ErrorMsg: " + e.getErrorMsg());
        }
        return listScalingGroupsResponse.getScalingGroups();
    }

    public ScalingGroups getScalingGroupById(AsClient client, String asGroupId) {
        ShowScalingGroupResponse showScalingGroupResponse = new ShowScalingGroupResponse();
        try {
            showScalingGroupResponse = client.showScalingGroup(new ShowScalingGroupRequest()
                    .withScalingGroupId(asGroupId));
        } catch (ClientRequestException e) {
            logger.error("[Scaling Group]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Scaling Group]RequestId: " + e.getRequestId());
            logger.error("[Scaling Group]ErrorCode: " + e.getErrorCode());
            logger.error("[Scaling Group]ErrorMsg: " + e.getErrorMsg());
        }
        return showScalingGroupResponse.getScalingGroup();
    }

    public List<ScalingConfiguration> getScalingConfigsListByName(AsClient client, String asConfigName) {
        ListScalingConfigsResponse listScalingConfigsResponse = new ListScalingConfigsResponse();
        try {
            listScalingConfigsResponse = client.listScalingConfigs(new ListScalingConfigsRequest()
                    .withScalingConfigurationName(asConfigName));
            logger.info(listScalingConfigsResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Scaling Configuration]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Scaling Configuration]RequestId: " + e.getRequestId());
            logger.error("[Scaling Configuration]ErrorCode: " + e.getErrorCode());
            logger.error("[Scaling Configuration]ErrorMsg: " + e.getErrorMsg());
        }
        return listScalingConfigsResponse.getScalingConfigurations();
    }

    public ScalingConfiguration getScalingConfigurationById(AsClient client, String asConfigId) {
        ShowScalingConfigResponse showScalingConfigResponse = new ShowScalingConfigResponse();
        try {
            showScalingConfigResponse = client.showScalingConfig(new ShowScalingConfigRequest()
                    .withScalingConfigurationId(asConfigId));
            logger.info(showScalingConfigResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Scaling Configuration]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Scaling Configuration]RequestId: " + e.getRequestId());
            logger.error("[Scaling Configuration]ErrorCode: " + e.getErrorCode());
            logger.error("[Scaling Configuration]ErrorMsg: " + e.getErrorMsg());
        }
        return showScalingConfigResponse.getScalingConfiguration();
    }

    public List<ScalingPolicyDetail> getScalingPoliciesListById(AsClient client, String asGroupId) {
        ListScalingPoliciesResponse listScalingPoliciesResponse = new ListScalingPoliciesResponse();
        try {
            listScalingPoliciesResponse = client.listScalingPolicies(new ListScalingPoliciesRequest()
                    .withScalingGroupId(asGroupId));
            logger.info(listScalingPoliciesResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Scaling Policies]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Scaling Policies]RequestId: " + e.getRequestId());
            logger.error("[Scaling Policies]ErrorCode: " + e.getErrorCode());
            logger.error("[Scaling Policies]ErrorMsg: " + e.getErrorMsg());
        }
        return listScalingPoliciesResponse.getScalingPolicies();
    }

    public ScalingPolicyDetail getScalingPolicy(AsClient client, String scalingPolicyId) {
        ShowScalingPolicyResponse showScalingPolicyResponse = new ShowScalingPolicyResponse();
        try {
            showScalingPolicyResponse = client.showScalingPolicy(new ShowScalingPolicyRequest()
                    .withScalingPolicyId(scalingPolicyId));
            logger.info(showScalingPolicyResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Scaling Policy]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Scaling Policy]RequestId: " + e.getRequestId());
            logger.error("[Scaling Policy]ErrorCode: " + e.getErrorCode());
            logger.error("[Scaling Policy]ErrorMsg: " + e.getErrorMsg());
        }
        return  showScalingPolicyResponse.getScalingPolicy();
    }

    public MetricAlarms getMetricAlarmsById(CesClient client, String alarmId) {
        ShowAlarmResponse showAlarmResponse = new ShowAlarmResponse();
        try {
            showAlarmResponse = client.showAlarm(new ShowAlarmRequest()
                    .withAlarmId(alarmId));
            logger.info(showAlarmResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[MetricAlarms]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[MetricAlarms]RequestId: " + e.getRequestId());
            logger.error("[MetricAlarms]ErrorCode: " + e.getErrorCode());
            logger.error("[MetricAlarms]ErrorMsg: " + e.getErrorMsg());
        }
        return showAlarmResponse.getMetricAlarms().get(0);
    }


    public List<ScalingGroupInstance> getInstancesListById(AsClient client, String groupId) {
        ListScalingInstancesResponse listScalingInstancesResponse = new ListScalingInstancesResponse();
        try {
            listScalingInstancesResponse = client.listScalingInstances(new ListScalingInstancesRequest()
                    .withScalingGroupId(groupId));
            logger.info(listScalingInstancesResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Image]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Image]RequestId: " + e.getRequestId());
            logger.error("[Image]ErrorCode: " + e.getErrorCode());
            logger.error("[Image]ErrorMsg: " + e.getErrorMsg());
        }
        logger.info("Instance Total Num: " + listScalingInstancesResponse.getScalingGroupInstances().size());
        return listScalingInstancesResponse.getScalingGroupInstances();
    }

    public List<SecurityGroups> getSecGroupsListByIds(List<String> secGroupIds) {
        List<SecurityGroups> secGroupList = new ArrayList<>();
        for (String secGroupId : secGroupIds) {
            SecurityGroups securityGroups = new SecurityGroups();
            securityGroups.setId(secGroupId);
            secGroupList.add(securityGroups);
        }
        return secGroupList;
    }

    //Need to update
    public List<Map<String, String>> getDiskCongfigMapByArray(String[][] diskConfig){
        List<Map<String, String>> diskList = new ArrayList<>();
        for (String[] config : diskConfig) {
            diskList.add(new Hashtable<String, String>(){{
                put("diskType", config[0]);
                put("volumeType", config[1]);
                put("diskSize", config[2]);
            }});
        }
        return diskList;
    }

    public List<Disk> getDiskList(List<Map<String, String>> disks) {
        List<Disk> diskList = new ArrayList<>();
        for (Map<String, String> diskConfig : disks) {
            diskList.add(new Disk()
                    .withDiskType(Disk.DiskTypeEnum.valueOf(diskConfig.get("diskType")))
                    .withVolumeType(Disk.VolumeTypeEnum.valueOf(diskConfig.get("volumeType")))
                    .withSize(Integer.valueOf(diskConfig.get("diskSize"))));
        }
        return diskList;
    }


    public List<Networks> getNetworksList(List<String> networkIds) {
        List<Networks> networksList = new ArrayList<>();
        for (String id : networkIds) {
            networksList.add(new Networks().withId(id));
        }
        return networksList;
    }



    public InstanceConfig getScalingInstanceConfig(String secGroupId, String flavors, String imageId, String keyPairName,
                                                   String[][] diskConfig, int bindwidthSize, String bindwidthType) {
        return new InstanceConfig().withSecurityGroups(new ArrayList<>(Arrays.asList(new SecurityGroups().withId(secGroupId))))
                .withFlavorRef(flavors)
                .withImageRef(imageId)
                .withKeyName(keyPairName)
                .withDisk(getDiskList(getDiskCongfigMapByArray(diskConfig)))
                .withPublicIp(new PublicIp()
                        .withEip(new Eip()
                                .withBandwidth(new Bandwidth()
                                        .withChargingMode(Bandwidth.ChargingModeEnum.BANDWIDTH)
                                        .withSize(bindwidthSize)
                                        .withShareType(Bandwidth.ShareTypeEnum.PER))
                                .withIpType(Eip.IpTypeEnum.valueOf(bindwidthType))));
    }

    public ScalingPolicyAction getPolicyActionWithNum(String option, int instanceNum) {
        ScalingPolicyAction scalingPolicyAction = new ScalingPolicyAction();

        scalingPolicyAction.setOperation(ScalingPolicyAction.OperationEnum.valueOf(option));
        scalingPolicyAction.setInstanceNumber(instanceNum);

        return scalingPolicyAction;
    }

    public static ScalingPolicyAction getPolicyActionWithPercentage(String option, int percentage) {
        ScalingPolicyAction scalingPolicyAction = new ScalingPolicyAction();

        scalingPolicyAction.setOperation(ScalingPolicyAction.OperationEnum.valueOf(option));
        scalingPolicyAction.setInstancePercentage(percentage);

        return scalingPolicyAction;
    }
}
