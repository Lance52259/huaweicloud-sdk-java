package com.huaweicloud.sdk.test.vpc;

import com.huaweicloud.sdk.core.exception.ClientRequestException;
import com.huaweicloud.sdk.vpc.v2.VpcClient;
import com.huaweicloud.sdk.vpc.v2.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class VpcRead {
    private static final Logger logger = LoggerFactory.getLogger(VpcRead.class);

    public List<Vpc> getVpcsListById(VpcClient client, String vpcId) {
        ListVpcsResponse listVpcsResponse = new ListVpcsResponse();
        try {
            listVpcsResponse = client.listVpcs(new ListVpcsRequest()
                    .withId(vpcId));
            logger.info(listVpcsResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Vpc]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Vpc]RequestId: " + e.getRequestId());
            logger.error("[Vpc]ErrorCode: " + e.getErrorCode());
            logger.error("[Vpc]ErrorMsg: " + e.getErrorMsg());
        }
        return listVpcsResponse.getVpcs();
    }

    public Vpc getVpcById(VpcClient client, String vpcId) {
        ShowVpcResponse showVpcResponse;
        try {
            showVpcResponse = client.showVpc(new ShowVpcRequest()
                    .withVpcId(vpcId));
            logger.info(showVpcResponse.toString());
        } catch (ClientRequestException e) {
            return null;
        }
        showVpc(showVpcResponse.getVpc());
        return showVpcResponse.getVpc();
    }

    public void showVpc(Vpc vpc) {
        logger.info("ID:"+vpc.getId());
        logger.info("Name:"+vpc.getName());
        logger.info("Description:"+vpc.getDescription());
        logger.info("Status:"+vpc.getStatus());
        logger.info("Cidr:"+vpc.getCidr());
        logger.info("EnterpriseProjectID:"+vpc.getEnterpriseProjectId());
        logger.info("Routes:"+vpc.getRoutes());
    }

    public List<Subnet> getSubnetsListById(VpcClient client, String vpcId) {
        ListSubnetsResponse listSubnetsResponse = new ListSubnetsResponse();
        try {
            listSubnetsResponse = client.listSubnets(new ListSubnetsRequest()
                    .withVpcId(vpcId));
            logger.info(listSubnetsResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Subnet]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Subnet]RequestId: " + e.getRequestId());
            logger.error("[Subnet]ErrorCode: " + e.getErrorCode());
            logger.error("[Subnet]ErrorMsg: " + e.getErrorMsg());
        }
        return listSubnetsResponse.getSubnets();
    }

    public Subnet getSubnetById(VpcClient client, String subnetId) {
        ShowSubnetResponse showSubnetResponse;
        try {
            showSubnetResponse = client.showSubnet(new ShowSubnetRequest()
                    .withSubnetId(subnetId));
        } catch (ClientRequestException e) {
            return null;
        }
        showSubnet(showSubnetResponse.getSubnet());
        return showSubnetResponse.getSubnet();
    }

    public void showSubnet(Subnet subnet) {
        logger.info("ID:"+subnet.getId());
        logger.info("Name:"+subnet.getName());
        logger.info("Description:"+subnet.getDescription());
        logger.info("Status:"+subnet.getStatus());
        logger.info("Cidr:"+subnet.getCidr());
        logger.info("VpcID:"+subnet.getVpcId());
        logger.info("AvailabilityZone:"+subnet.getAvailabilityZone());
        logger.info("CidrV6:"+subnet.getCidrV6());
        logger.info("GatewayIp:"+subnet.getGatewayIp());
        logger.info("GatewayIpV6:"+subnet.getGatewayIpV6());
        logger.info("PrimaryDns:"+subnet.getPrimaryDns());
        logger.info("SecondaryDns:"+subnet.getSecondaryDns());
        logger.info("DhcpEnable:"+subnet.getDhcpEnable());
        logger.info("DnsList:"+subnet.getDnsList());
        logger.info("Ipv6Enable:"+subnet.getIpv6Enable());
        logger.info("ExtraDhcpOpts:"+subnet.getExtraDhcpOpts());
    }

    public List<SecurityGroup> getSecurityGroupsListById(VpcClient client, String vpcId) {
        ListSecurityGroupsResponse listSecurityGroupsResponse = new ListSecurityGroupsResponse();
        try {
            listSecurityGroupsResponse = client.listSecurityGroups(new ListSecurityGroupsRequest()
                    .withVpcId(vpcId));
            logger.info(listSecurityGroupsResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[SecurityGroup]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[SecurityGroup]RequestId: " + e.getRequestId());
            logger.error("[SecurityGroup]ErrorCode: " + e.getErrorCode());
            logger.error("[SecurityGroup]ErrorMsg: " + e.getErrorMsg());
        }
        return listSecurityGroupsResponse.getSecurityGroups();
    }

    public SecurityGroup getSecurityGroupById(VpcClient client, String securityGroupId) {
        ShowSecurityGroupResponse showSecurityGroupResponse;
        try {
            showSecurityGroupResponse = client.showSecurityGroup(new ShowSecurityGroupRequest()
                    .withSecurityGroupId(securityGroupId));
            logger.info(showSecurityGroupResponse.toString());
        } catch (ClientRequestException e) {
            return null;
        }
        showSecurityGroup(showSecurityGroupResponse.getSecurityGroup());
        return showSecurityGroupResponse.getSecurityGroup();
    }

    public void showSecurityGroup(SecurityGroup securityGroup) {
        logger.info("ID:"+securityGroup.getId());
        logger.info("Name:"+securityGroup.getName());
        logger.info("Description:"+securityGroup.getDescription());
        logger.info("VpcID:"+securityGroup.getVpcId());
        logger.info("SecurityGroupRules:"+securityGroup.getSecurityGroupRules());
        logger.info("EnterpriseProjectID:"+securityGroup.getEnterpriseProjectId());
    }

    public List<SecurityGroupRule> getSecurityGroupRulesListById(VpcClient client, String securityGroupId) {
        ListSecurityGroupRulesResponse listSecurityGroupRulesResponse = new ListSecurityGroupRulesResponse();
        try {
            listSecurityGroupRulesResponse = client.listSecurityGroupRules(new ListSecurityGroupRulesRequest()
                    .withSecurityGroupId(securityGroupId));
            logger.info(listSecurityGroupRulesResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[SecurityGroup]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[SecurityGroup]RequestId: " + e.getRequestId());
            logger.error("[SecurityGroup]ErrorCode: " + e.getErrorCode());
            logger.error("[SecurityGroup]ErrorMsg: " + e.getErrorMsg());
        }
        return listSecurityGroupRulesResponse.getSecurityGroupRules();
    }

    public SecurityGroupRule getSecurityGroupRuleById(VpcClient client, String securityGroupRuleId) {
        ShowSecurityGroupRuleResponse showSecurityGroupRuleResponse;
        try {
            showSecurityGroupRuleResponse = client.showSecurityGroupRule(new ShowSecurityGroupRuleRequest()
                    .withSecurityGroupRuleId(securityGroupRuleId));
            logger.info(showSecurityGroupRuleResponse.toString());
        } catch (ClientRequestException e) {
            return null;
        }
        showSecurityGroupRule(showSecurityGroupRuleResponse.getSecurityGroupRule());
        return showSecurityGroupRuleResponse.getSecurityGroupRule();
    }

    public void showSecurityGroupRule(SecurityGroupRule securityGroupRule) {
        logger.info("ID:"+securityGroupRule.getId());
        logger.info("securityGroupID:"+securityGroupRule.getSecurityGroupId());
        logger.info("Direction:"+securityGroupRule.getDirection());
        logger.info("Description:"+securityGroupRule.getDescription());
        logger.info("Ethertype:"+securityGroupRule.getEthertype());
        logger.info("Protoco:"+securityGroupRule.getProtocol());
        logger.info("PortRangeMax:"+securityGroupRule.getPortRangeMax());
        logger.info("PortRangeMin:"+securityGroupRule.getPortRangeMin());
        logger.info("RemoteGroupID:"+securityGroupRule.getRemoteGroupId());
        logger.info("RemoteIpPrefix:"+securityGroupRule.getRemoteIpPrefix());
        logger.info("TenantID:"+securityGroupRule.getTenantId());
    }
}
