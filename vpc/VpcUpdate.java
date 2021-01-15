package com.huaweicloud.sdk.test.vpc;

import com.huaweicloud.sdk.core.exception.ClientRequestException;
import com.huaweicloud.sdk.vpc.v2.VpcClient;

import com.huaweicloud.sdk.vpc.v2.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class VpcUpdate {
    private static final Logger logger = LoggerFactory.getLogger(VpcUpdate.class);

    VpcCheck vpcCheck = new VpcCheck();

    public void updateVpc(VpcClient client, String vpcName, String description, String cidr) throws InterruptedException {
        UpdateVpcResponse updateVpcResponse = new UpdateVpcResponse();
        try {
            updateVpcResponse = client.updateVpc(new UpdateVpcRequest()
                    .withBody(new UpdateVpcRequestBody()
                            .withVpc(new UpdateVpcOption()
                                    .withName(vpcName)
                                    .withCidr(description)
                                    .withCidr(cidr))));
            logger.info(updateVpcResponse.toString());

        } catch (ClientRequestException e) {
            logger.error("[Vpc]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Vpc]RequestId: " + e.getRequestId());
            logger.error("[Vpc]ErrorCode: " + e.getErrorCode());
            logger.error("[Vpc]ErrorMsg: " + e.getErrorMsg());
        }
        if (!vpcCheck.checkVpcInActive(client, updateVpcResponse.getVpc().getId(), 3, 3, 5, 1)) {
            logger.error("[Error]Vpc update failed.");
        }
    }

    public void updateSubnet(VpcClient client, String subnetName, String description, String cidr) throws InterruptedException {
        UpdateSubnetResponse updateSubnetResponse = new UpdateSubnetResponse();
        try {
            updateSubnetResponse = client.updateSubnet(new UpdateSubnetRequest()
                    .withBody(new UpdateSubnetRequestBody()
                            .withSubnet(new UpdateSubnetOption()
                                    .withName(subnetName)
                                    .withDescription(description))));
            logger.info(updateSubnetResponse.toString());

        } catch (ClientRequestException e) {
            logger.error("[Vpc]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Vpc]RequestId: " + e.getRequestId());
            logger.error("[Vpc]ErrorCode: " + e.getErrorCode());
            logger.error("[Vpc]ErrorMsg: " + e.getErrorMsg());
        }
        if (!vpcCheck.checkSubnetInActive(client, updateSubnetResponse.getSubnet().getId(), 3, 3, 5, 1)) {
            logger.error("[Error]Subnet create failed.");
        }
    }
}
