package com.huaweicloud.sdk.test.as;

import com.huaweicloud.sdk.as.v1.AsClient;
import com.huaweicloud.sdk.as.v1.model.*;
import com.huaweicloud.sdk.core.exception.ClientRequestException;
import com.huaweicloud.sdk.test.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AsUpdate {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void updateAsGroupOnlyInstanceNum(AsClient client, String asGroupId, String asGroupName, int desireNum,
            int minNum, int maxNum, String vpcId, List<Networks> networksList) {

        UpdateScalingGroupRequest updateScalingGroupRequest = new UpdateScalingGroupRequest();
        UpdateScalingGroupResponse updateScalingGroupResponse = new UpdateScalingGroupResponse();

        try {
            UpdateScalingGroupRequestBody updateScalingGroupRequestBody = new UpdateScalingGroupRequestBody()
                    .withScalingGroupName(asGroupName)
                    .withDesireInstanceNumber(desireNum)
                    .withMinInstanceNumber(minNum)
                    .withMaxInstanceNumber(maxNum)
                    .withNetworks(networksList);
            updateScalingGroupResponse = client.updateScalingGroup(updateScalingGroupRequest
                    .withBody(updateScalingGroupRequestBody)
                    .withScalingGroupId(asGroupId));
            logger.info(updateScalingGroupResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Auto Scaling Group]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Auto Scaling Group]RequestId: " + e.getRequestId());
            logger.error("[Auto Scaling Group]ErrorCode: " + e.getErrorCode());
            logger.error("[Auto Scaling Group]ErrorMsg: " + e.getErrorMsg());
        }
    }

    public static void updateScalingGroupStatus(AsClient client, String scalingGroupId, String action) {

        EnableOrDisableScalingGroupRequest enableOrDisableScalingGroupRequest = new EnableOrDisableScalingGroupRequest();
        EnableOrDisableScalingGroupRequestBody enableOrDisableScalingGroupRequestBody = new EnableOrDisableScalingGroupRequestBody();
        EnableOrDisableScalingGroupResponse enableOrDisableScalingGroupResponse;

        try {
            enableOrDisableScalingGroupRequestBody.setAction(EnableOrDisableScalingGroupRequestBody.ActionEnum.valueOf(action));

            enableOrDisableScalingGroupResponse = client.enableOrDisableScalingGroup(enableOrDisableScalingGroupRequest
                    .withBody(enableOrDisableScalingGroupRequestBody)
                    .withScalingGroupId(scalingGroupId));
            logger.info("[response]：" + enableOrDisableScalingGroupResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Scaling Policy-Create]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Scaling Policy-Create]RequestId: " + e.getRequestId());
            logger.error("[Scaling Policy-Create]ErrorCode: " + e.getErrorCode());
            logger.error("[Scaling Policy-Create]ErrorMsg: " + e.getErrorMsg());
        }
    }
}
