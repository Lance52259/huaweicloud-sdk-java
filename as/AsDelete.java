package com.huaweicloud.sdk.test.as;

import com.huaweicloud.sdk.as.v1.AsClient;
import com.huaweicloud.sdk.as.v1.model.*;
import com.huaweicloud.sdk.ces.v1.CesClient;
import com.huaweicloud.sdk.ces.v1.model.DeleteAlarmRequest;
import com.huaweicloud.sdk.ces.v1.model.DeleteAlarmResponse;
import com.huaweicloud.sdk.core.exception.ClientRequestException;
import com.huaweicloud.sdk.test.Application;
import com.huaweicloud.sdk.test.vpc.VpcDelete;
import com.huaweicloud.sdk.test.vpc.VpcRead;
import com.huaweicloud.sdk.vpc.v2.VpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AsDelete {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    AsRead   asRead   = new AsRead();
    AsUpdate asUpdate = new AsUpdate();
    AsCheck  asCheck  = new AsCheck();

    public void deleteScalingGroup(AsClient client, String asGroupId, String isDelete) throws InterruptedException {
        DeleteScalingGroupRequest deleteScalingGroupRequest = new DeleteScalingGroupRequest();
        DeleteScalingGroupResponse deleteScalingGroupResponse;

        asUpdate.updateScalingGroupStatus(client, asGroupId, "pause");
        ScalingGroups scalingGroups = asRead.getScalingGroupById(client, asGroupId);

        asUpdate.updateAsGroupOnlyInstanceNum(client, asGroupId, scalingGroups.getScalingGroupName(), 0, 0,
                scalingGroups.getMaxInstanceNumber(), scalingGroups.getVpcId(), scalingGroups.getNetworks());

        List<ScalingGroupInstance> instanceList = asRead.getInstancesListById(client, asGroupId);
        for (ScalingGroupInstance instance : instanceList) {
            deleteScalingInstance(client, asGroupId, instance.getInstanceId(), isDelete);
        }
        try {
            deleteScalingGroupRequest.setScalingGroupId(asGroupId);
//            deleteScalingGroupRequest.setForceDelete(DeleteScalingGroupRequest.ForceDeleteEnum.TRUE);
            deleteScalingGroupResponse = client.deleteScalingGroup(deleteScalingGroupRequest);
            logger.info(deleteScalingGroupResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Scaling Config]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Scaling Config]RequestId: " + e.getRequestId());
            logger.error("[Scaling Config]ErrorCode: " + e.getErrorCode());
            logger.error("[Scaling Config]ErrorMsg: " + e.getErrorMsg());
        }
        if (!asCheck.checkScalingGroupDelete(client, asGroupId, 3, 3, 60)) {
            logger.error("[Error]Scaling Group delete failed.");
        }
    }

    public void deleteScalingInstance(AsClient client, String asGroupId, String instanceId, String isDelete) throws InterruptedException {
        DeleteScalingInstanceResponse deleteScalingInstanceResponse;
        try {
            deleteScalingInstanceResponse = client.deleteScalingInstance(new DeleteScalingInstanceRequest()
                    .withInstanceId(instanceId)
                    .withInstanceDelete(DeleteScalingInstanceRequest.InstanceDeleteEnum.valueOf(isDelete.toLowerCase())));
            logger.info(deleteScalingInstanceResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Scaling Instance-Delete]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Scaling Instance-Delete]RequestId: " + e.getRequestId());
            logger.error("[Scaling Instance-Delete]ErrorCode: " + e.getErrorCode());
            logger.error("[Scaling Instance-Delete]ErrorMsg: " + e.getErrorMsg());
        }
        if (!asCheck.checkScalingInstanceDelete(client, asGroupId, instanceId, 3, 5, 100)){
            logger.error("[Error]Not all Scaling Instance were deleted.");
        }
    }

    public void deleteScalingConfig(AsClient client, String configId) throws InterruptedException {
        DeleteScalingConfigRequest deleteScalingConfigRequest = new DeleteScalingConfigRequest();
        DeleteScalingConfigResponse deleteScalingConfigResponse;
        try {
            deleteScalingConfigRequest.setScalingConfigurationId(configId);
            deleteScalingConfigResponse = client.deleteScalingConfig(deleteScalingConfigRequest);
            logger.info(deleteScalingConfigResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Scaling Config-Delete]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Scaling Config-Delete]RequestId: " + e.getRequestId());
            logger.error("[Scaling Config-Delete]ErrorCode: " + e.getErrorCode());
            logger.error("[Scaling Config-Delete]ErrorMsg: " + e.getErrorMsg());
        }
        if (!asCheck.checkScalingConfigDelete(client, configId, 3, 3, 5)) {
            logger.error("[Error]Alarms delete failed.");
        }
    }

    public void deleteAlarm(CesClient client, String alarmId) throws InterruptedException {
        DeleteAlarmRequest deleteAlarmRequest = new DeleteAlarmRequest();
        DeleteAlarmResponse deleteAlarmResponse;
        try {
            deleteAlarmRequest.setAlarmId(alarmId);
            deleteAlarmResponse = client.deleteAlarm(deleteAlarmRequest);
            logger.info(deleteAlarmResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Scaling Config-Delete]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Scaling Config-Delete]RequestId: " + e.getRequestId());
            logger.error("[Scaling Config-Delete]ErrorCode: " + e.getErrorCode());
            logger.error("[Scaling Config-Delete]ErrorMsg: " + e.getErrorMsg());
        }
        if (!asCheck.checkAlarmsDelete(client, alarmId, 3, 3, 5)) {
            logger.error("[Error]Alarms delete failed.");
        }
    }

    public void deleteScalingPolicy(AsClient client, String policyId) throws InterruptedException {
        DeleteScalingPolicyRequest deleteScalingPolicyRequest = new DeleteScalingPolicyRequest();
        DeleteScalingPolicyResponse deleteScalingPolicyResponse;
        try {
            deleteScalingPolicyRequest.setScalingPolicyId(policyId);
            deleteScalingPolicyResponse = client.deleteScalingPolicy(deleteScalingPolicyRequest);
            logger.info(deleteScalingPolicyResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Scaling Config-Delete]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Scaling Config-Delete]RequestId: " + e.getRequestId());
            logger.error("[Scaling Config-Delete]ErrorCode: " + e.getErrorCode());
            logger.error("[Scaling Config-Delete]ErrorMsg: " + e.getErrorMsg());
        }
        if (!asCheck.checkScalingPolicyDelete(client, policyId, 3, 3, 5)) {
            logger.error("[Error]Scaling policy delete failed.");
        }
    }
}
