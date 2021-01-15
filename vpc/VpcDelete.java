package com.huaweicloud.sdk.test.vpc;

import com.huaweicloud.sdk.core.exception.ClientRequestException;
import com.huaweicloud.sdk.vpc.v2.VpcClient;
import com.huaweicloud.sdk.vpc.v2.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class VpcDelete {
    private static final Logger logger = LoggerFactory.getLogger(VpcDelete.class);

    VpcRead vpcRead = new VpcRead();
    VpcUpdate vpcUpdate = new VpcUpdate();
    VpcCheck vpcCheck = new VpcCheck();

    public void deleteVpc(VpcClient client, String vpcId) throws InterruptedException {
        DeleteVpcResponse deleteVpcResponse;
        try {
            deleteVpcResponse = client.deleteVpc(new DeleteVpcRequest().withVpcId(vpcId));
            logger.info(deleteVpcResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[VPC]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[VPC]RequestId: " + e.getRequestId());
            logger.error("[VPC]ErrorCode: " + e.getErrorCode());
            logger.error("[VPC]ErrorMsg: " + e.getErrorMsg());
        }
        if (!vpcCheck.checkVpcDelete(client, vpcId, 3, 3, 5)) {
            logger.error("[Error]Vpc delete failed.");
        }
    }

    public void deleteSubnet(VpcClient client, String vpcId, String subnetId) throws InterruptedException {
        DeleteSubnetResponse deleteSubnetResponse;
        try {
            deleteSubnetResponse = client.deleteSubnet(new DeleteSubnetRequest().withVpcId(vpcId).withSubnetId(subnetId));
            logger.info(deleteSubnetResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Subnet]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Subnet]RequestId: " + e.getRequestId());
            logger.error("[Subnet]ErrorCode: " + e.getErrorCode());
            logger.error("[Subnet]ErrorMsg: " + e.getErrorMsg());
        }
        if (!vpcCheck.checkSubnetDelete(client, vpcId, 3, 3, 5)) {
            logger.error("[Error]Subnet delete failed.");
        }
    }

    public void deleteSecurityGroup(VpcClient client, String vpcId, String securityGroupId) throws InterruptedException {
        DeleteSecurityGroupResponse deleteSecurityGroupResponse;
//        List<SecurityGroupRule> securityGroupRulesList = vpcRead.getSecurityGroupRulesListById(client, securityGroupId);
//        for (SecurityGroupRule securityGroupRule : securityGroupRulesList) {
//            deleteSecurityGroupRule(client, securityGroupRule.getId());
//        }
        try {
            deleteSecurityGroupResponse = client.deleteSecurityGroup(new DeleteSecurityGroupRequest().withSecurityGroupId(securityGroupId));
            logger.info(deleteSecurityGroupResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Security Group]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Security Group]RequestId: " + e.getRequestId());
            logger.error("[Security Group]ErrorCode: " + e.getErrorCode());
            logger.error("[Security Group]ErrorMsg: " + e.getErrorMsg());
        }
        if (!vpcCheck.checkSecurityGroupDelete(client, securityGroupId, 3, 3, 5)) {
            logger.error("[Error]Security Group delete failed.");
        }
    }

    public void deleteSecurityGroupRule(VpcClient client, String securityGroupRuleId) throws InterruptedException {
        DeleteSecurityGroupRuleResponse deleteSecurityGroupRuleResponse;
        try {
            deleteSecurityGroupRuleResponse = client.deleteSecurityGroupRule(new DeleteSecurityGroupRuleRequest().withSecurityGroupRuleId(securityGroupRuleId));
            logger.info(deleteSecurityGroupRuleResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Security Group]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Security Group]RequestId: " + e.getRequestId());
            logger.error("[Security Group]ErrorCode: " + e.getErrorCode());
            logger.error("[Security Group]ErrorMsg: " + e.getErrorMsg());
        }
        if (!vpcCheck.checkSecurityGroupRuleDelete(client, securityGroupRuleId, 3, 3, 5)) {
            logger.error("[Error]Security Group Rule delete failed.");
        }
    }
}
