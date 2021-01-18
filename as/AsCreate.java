package com.huaweicloud.sdk.test.as;

import com.huaweicloud.sdk.as.v1.AsClient;
import com.huaweicloud.sdk.as.v1.model.*;
import com.huaweicloud.sdk.ces.v1.CesClient;
import com.huaweicloud.sdk.ces.v1.model.*;
import com.huaweicloud.sdk.core.exception.ClientRequestException;
import com.huaweicloud.sdk.test.Application;
import com.huaweicloud.sdk.vpc.v2.VpcClient;
import com.huaweicloud.sdk.vpc.v2.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AsCreate {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    AsCheck asCheck = new AsCheck();
    public static AsRead asRead;

    public String createScalingConfiguration(AsClient client, InstanceConfig instanceConfig, String asConfigName) throws InterruptedException {
        CreateScalingConfigResponse createScalingConfigResponse = new CreateScalingConfigResponse();

        try {
            createScalingConfigResponse = client.createScalingConfig(new CreateScalingConfigRequest()
                    .withBody(new CreateScalingConfigRequestBody()
                            .withInstanceConfig(instanceConfig)
                            .withScalingConfigurationName(asConfigName)));
            logger.info(createScalingConfigResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Auto Scaling Configuration]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Auto Scaling Configuration]RequestId: " + e.getRequestId());
            logger.error("[Auto Scaling Configuration]ErrorCode: " + e.getErrorCode());
            logger.error("[Auto Scaling Configuration]ErrorMsg: " + e.getErrorMsg());
        }
        if (!asCheck.checkScalingConfigInActive(client, createScalingConfigResponse.getScalingConfigurationId(), 3, 3, 5)) {
            logger.error("[Error]Vpc create failed.");
        }
        return createScalingConfigResponse.getScalingConfigurationId();
    }

    public String createAutoScalingGroup(AsClient client, String asGroupName, String asConfigId, int desireNum,
            int minNum, int maxNum, int coolDownTime, String listenerId, List<LbaasListeners> lbaasListenersList,
            List<String> availabaleZonesList, List<SecurityGroups> securityGroupsList, String vpcId, List<Networks> networksList,
            String auditMethod, int auditTime, int auditGracePeriod, String terminaatePolicy, boolean deletePublicIp, String azPriorityPolicy) throws InterruptedException {
        CreateScalingGroupResponse createScalingGroupResponse = new CreateScalingGroupResponse();

        try {
            CreateScalingGroupRequestBody createScalingGroupRequestBody = new CreateScalingGroupRequestBody()
                            .withScalingGroupName(asGroupName)
                            .withScalingConfigurationId(asConfigId)
                            .withDesireInstanceNumber(desireNum)
                            .withMinInstanceNumber(minNum)
                            .withMaxInstanceNumber(maxNum)
                            .withCoolDownTime(coolDownTime)
                            .withLbListenerId(listenerId)
                            .withNetworks(networksList) //getNetworksList
                            .withSecurityGroups(securityGroupsList)
                            .withVpcId(vpcId)
                            .withHealthPeriodicAuditMethod(CreateScalingGroupRequestBody.HealthPeriodicAuditMethodEnum.valueOf(auditMethod.toUpperCase()))
                            .withHealthPeriodicAuditTime(CreateScalingGroupRequestBody.HealthPeriodicAuditTimeEnum.valueOf(auditTime))
                            .withHealthPeriodicAuditGracePeriod(auditGracePeriod)
                            .withInstanceTerminatePolicy(CreateScalingGroupRequestBody.InstanceTerminatePolicyEnum.valueOf(terminaatePolicy.toUpperCase()))
                            .withDeletePublicip(deletePublicIp)
                            .withMultiAzPriorityPolicy(CreateScalingGroupRequestBody.MultiAzPriorityPolicyEnum.valueOf(azPriorityPolicy.toUpperCase()));
            if(lbaasListenersList.size() != 0) {
                createScalingGroupRequestBody.setLbaasListeners(lbaasListenersList);
            }
            if(availabaleZonesList.size() != 0 ) {
                createScalingGroupRequestBody.setAvailableZones(availabaleZonesList);
            }
            createScalingGroupResponse = client.createScalingGroup(new CreateScalingGroupRequest()
                    .withBody(createScalingGroupRequestBody));
            logger.info(createScalingGroupResponse.toString());

        } catch (ClientRequestException e) {
            logger.error("[Auto Scaling Group]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Auto Scaling Group]RequestId: " + e.getRequestId());
            logger.error("[Auto Scaling Group]ErrorCode: " + e.getErrorCode());
            logger.error("[Auto Scaling Group]ErrorMsg: " + e.getErrorMsg());
        }
        if (!asCheck.checkScalingGroupInActive(client, createScalingGroupResponse.getScalingGroupId(), 3, 3, 5)) {
            logger.error("[Error]Auto Scaling Group create failed.");
        }
        return createScalingGroupResponse.getScalingGroupId();
    }

    public String createScalingPolicyWithAlarm(AsClient client, String policyName, String asGroupId,
            String operation, int instanceNum, String alarmId, int coolDownTime) throws InterruptedException {
        CreateScalingPolicyResponse createScalingPolicyResponse = new CreateScalingPolicyResponse();
        try {
            createScalingPolicyResponse = client.createScalingPolicy(new CreateScalingPolicyRequest()
                    .withBody(new CreateScalingPolicyRequestBody()
                            .withScalingPolicyName(policyName)
                            .withScalingGroupId(asGroupId)
                            .withScalingPolicyType(CreateScalingPolicyRequestBody.ScalingPolicyTypeEnum.ALARM)
                            .withAlarmId(alarmId)
                            .withScalingPolicyAction(new ScalingPolicyAction()
                                    .withOperation(ScalingPolicyAction.OperationEnum.valueOf(operation.toUpperCase()))
                                    .withInstanceNumber(instanceNum))
                            .withCoolDownTime(coolDownTime)));
            logger.info("[response]："+createScalingPolicyResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Scaling Policy-Create]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Scaling Policy-Create]RequestId: " + e.getRequestId());
            logger.error("[Scaling Policy-Create]ErrorCode: " + e.getErrorCode());
            logger.error("[Scaling Policy-Create]ErrorMsg: " + e.getErrorMsg());
        }
        if (!asCheck.checkScalingPolicyInActive(client, createScalingPolicyResponse.getScalingPolicyId(), 3, 3, 5)) {
            logger.error("[Error]Auto Scaling Group create failed.");
        }
        return createScalingPolicyResponse.getScalingPolicyId();
    }

    public String createAlarm(CesClient client, String alarmName, String metricName, String namespace, String asGroupId,
                              int period, String filter, String comparisonOperator, double threshold, String unit, int count) {
        CreateAlarmResponse createAlarmResponse = new CreateAlarmResponse();
        try {
            createAlarmResponse = client.createAlarm(new CreateAlarmRequest()
                    .withBody(new CreateAlarmRequestBody()
                            .withAlarmName(alarmName)
                            .withMetric(new MetricInfoForAlarm()
                                    .withMetricName(metricName)
                                    .withNamespace(namespace)
                                    .withDimensions(new ArrayList<>(Arrays.asList(new MetricsDimension()
                                            .withName("AutoScalingGroup")
                                            .withValue(asGroupId)))))
                            .withCondition(new Condition()
                                    .withPeriod(period)
                                    .withFilter(filter)
                                    .withComparisonOperator(comparisonOperator)
                                    .withValue(threshold)
                                    .withUnit(unit)
                                    .withCount(count))
                            .withAlarmActionEnabled(true)
                            .withAlarmActions(new ArrayList<AlarmActions>(Arrays.asList(new AlarmActions()
                                    .withType("autoscaling")
                                    .withNotificationList(new ArrayList<>()))))));
            logger.info("[response]：" + createAlarmResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Scaling Policy-Create]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Scaling Policy-Create]RequestId: " + e.getRequestId());
            logger.error("[Scaling Policy-Create]ErrorCode: " + e.getErrorCode());
            logger.error("[Scaling Policy-Create]ErrorMsg: " + e.getErrorMsg());
        }

        return createAlarmResponse.getAlarmId();
    }



}
