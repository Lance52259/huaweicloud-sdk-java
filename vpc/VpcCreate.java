package com.huaweicloud.sdk.test.vpc;

import com.huaweicloud.sdk.core.exception.ClientRequestException;
import com.huaweicloud.sdk.test.Application;
import com.huaweicloud.sdk.vpc.v2.VpcClient;
import com.huaweicloud.sdk.vpc.v2.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VpcCreate {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    VpcCheck vpcCheck = new VpcCheck();

    public Vpc createVpc(VpcClient client, String name, String cidr) throws InterruptedException {
        CreateVpcResponse createVpcResponse = new CreateVpcResponse();
        try {
            createVpcResponse = client.createVpc(new CreateVpcRequest()
                    .withBody(new CreateVpcRequestBody()
                            .withVpc(new CreateVpcOption()
                                    .withName(name)
                                    .withCidr(cidr))));
            logger.info(createVpcResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[VPC]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[VPC]RequestId: " + e.getRequestId());
            logger.error("[VPC]ErrorCode: " + e.getErrorCode());
            logger.error("[VPC]ErrorMsg: " + e.getErrorMsg());
        }
        if (!vpcCheck.checkVpcInActive(client, createVpcResponse.getVpc().getId(), 3, 3, 5, 1)) {
            logger.error("[Error]Vpc create failed.");
        }
        return createVpcResponse.getVpc();
    }

    public Subnet createSubnet(VpcClient client, String name, String cidr, String gatewayIp, String vpcId) throws InterruptedException {
        CreateSubnetResponse createSubnetResponse = new CreateSubnetResponse();
        try {
            createSubnetResponse = client.createSubnet(new CreateSubnetRequest()
                    .withBody(new CreateSubnetRequestBody()
                            .withSubnet(new CreateSubnetOption()
                                    .withName(name)
                                    .withCidr(cidr)
                                    .withGatewayIp(gatewayIp)
                                    .withVpcId(vpcId))));
            logger.info(createSubnetResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Subnet]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Subnet]RequestId: " + e.getRequestId());
            logger.error("[Subnet]ErrorCode: " + e.getErrorCode());
            logger.error("[Subnet]ErrorMsg: " + e.getErrorMsg());
        }
        if (!vpcCheck.checkSubnetInActive(client, createSubnetResponse.getSubnet().getId(), 3, 3, 5, 1)) {
            logger.error("[Error]Subnet create failed.");
        }
        return createSubnetResponse.getSubnet();
    }

    public SecurityGroup createSecurityGroup(VpcClient client, String vpcId, String secGroupName) throws InterruptedException {
        CreateSecurityGroupResponse createSecurityGroupsResponse = new CreateSecurityGroupResponse();
        try {
            createSecurityGroupsResponse = client.createSecurityGroup(new CreateSecurityGroupRequest()
                    .withBody(new CreateSecurityGroupRequestBody()
                            .withSecurityGroup(new CreateSecurityGroupOption()
                                    .withVpcId(vpcId)
                                    .withName(secGroupName))));
            logger.info(createSecurityGroupsResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Security Group]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Security Group]RequestId: " + e.getRequestId());
            logger.error("[Security Group]ErrorCode: " + e.getErrorCode());
            logger.error("[Security Group]ErrorMsg: " + e.getErrorMsg());
        }
        if (!vpcCheck.checkSecurityGroupExist(client, createSecurityGroupsResponse.getSecurityGroup().getId(), 3, 3, 5)) {
            logger.error("[Error]Security Group create failed.");
        }
        return createSecurityGroupsResponse.getSecurityGroup();
    }

    public SecurityGroupRule createRuleWithRemoteIpPrefix(VpcClient client, String securityGroupId, String ipVersion,
            String direction, int portMax, int portMin, String protocol, String remoteIpPrefix) {
        CreateSecurityGroupRuleResponse createSecurityGroupRuleResponse = new CreateSecurityGroupRuleResponse();
        CreateSecurityGroupRuleOption createSecurityGroupRuleOption = new CreateSecurityGroupRuleOption();
        try {
            createSecurityGroupRuleOption.withSecurityGroupId(securityGroupId)
                    .withEthertype(ipVersion)
                    .withDirection(direction)
                    .withProtocol(protocol)
                    .withRemoteIpPrefix(remoteIpPrefix);
            if(protocol.equals("icmp") || protocol.equals("ICMP")) {
                if(portMax != 0 || portMin != 0) {
                    logger.error("[Security Group Rule]Icmp doesn't need to set port.");
                    return null;
                }
            }else {
                createSecurityGroupRuleOption.withPortRangeMax(portMax);
                createSecurityGroupRuleOption.withPortRangeMin(portMin);
            }
            createSecurityGroupRuleResponse = client.createSecurityGroupRule(new CreateSecurityGroupRuleRequest()
                    .withBody(new CreateSecurityGroupRuleRequestBody()
                            .withSecurityGroupRule(createSecurityGroupRuleOption)));
            logger.info(createSecurityGroupRuleResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Security Group Rule]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Security Group Rule]RequestId: " + e.getRequestId());
            logger.error("[Security Group Rule]ErrorCode: " + e.getErrorCode());
            logger.error("[Security Group Rule]ErrorMsg: " + e.getErrorMsg());
        }
        return createSecurityGroupRuleResponse.getSecurityGroupRule();
    }

    public SecurityGroupRule createRuleWithRemoteGroupId(VpcClient client, String securityGroupId, String ipVersion,
            String direction, int portMax, int portMin, String protocol, String remoteGroupId) {
        CreateSecurityGroupRuleResponse createSecurityGroupRuleResponse = new CreateSecurityGroupRuleResponse();
        CreateSecurityGroupRuleOption createSecurityGroupRuleOption = new CreateSecurityGroupRuleOption();
        try {
            createSecurityGroupRuleOption.withSecurityGroupId(securityGroupId)
                    .withEthertype(ipVersion)
                    .withDirection(direction)
                    .withProtocol(protocol)
                    .withRemoteGroupId(remoteGroupId);
            if(protocol.equals("icmp") || protocol.equals("ICMP")) {
                if(portMax != 0 || portMin != 0) {
                    logger.error("[Security Group Rule]Icmp doesn't need to set port.");
                    return null;
                }
            }else {
                createSecurityGroupRuleOption.withPortRangeMax(portMax);
                createSecurityGroupRuleOption.withPortRangeMin(portMin);
            }
            createSecurityGroupRuleResponse = client.createSecurityGroupRule(new CreateSecurityGroupRuleRequest()
                    .withBody(new CreateSecurityGroupRuleRequestBody()
                            .withSecurityGroupRule(createSecurityGroupRuleOption)));
            logger.info(createSecurityGroupRuleResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Security Group Rule]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Security Group Rule]RequestId: " + e.getRequestId());
            logger.error("[Security Group Rule]ErrorCode: " + e.getErrorCode());
            logger.error("[Security Group Rule]ErrorMsg: " + e.getErrorMsg());
        }
        return createSecurityGroupRuleResponse.getSecurityGroupRule();
    }
}
