package com.huaweicloud.sdk.test.elb;

import com.huaweicloud.sdk.core.exception.ClientRequestException;
import com.huaweicloud.sdk.elb.v2.ElbClient;
import com.huaweicloud.sdk.elb.v2.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElbRead {
    private static final Logger logger = LoggerFactory.getLogger(ElbRead.class);

    ElbCheck elbCheck = new ElbCheck();

    /**
     * Querying Elastic Load Balancer Entity according to locdbalancerId By Huaweicloud API
     * @param client Specifies the ELB client.
     * @param loadbalancerId The id of Elastic Load Balancer
     * @return The Object entity of Elastic Load Balancer
     */
    public LoadbalancerResp getLoadBalancerById(ElbClient client, String loadbalancerId) {
        ShowLoadbalancerResponse showLoadbalancerResponse = new ShowLoadbalancerResponse();
        try {
            showLoadbalancerResponse = new ShowLoadbalancerResponse()
                    .withLoadbalancer(new LoadbalancerResp()
                            .withId(loadbalancerId));
            logger.info(showLoadbalancerResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Elastic Load Balancer]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Elastic Load Balancer]RequestId: " + e.getRequestId());
            logger.error("[Elastic Load Balancer]ErrorCode: " + e.getErrorCode());
            logger.error("[Elastic Load Balancer]ErrorMsg: " + e.getErrorMsg());
        }
        return showLoadbalancerResponse.getLoadbalancer();
    }

    /**
     * Querying ELB listener Entity according to listenerId By Huaweicloud API
     * @param client Specifies the ELB client.
     * @param listenerId The id of ELB listener
     * @return The Object entity of ELB listener
     */
    public ListenerResp getListenerById(ElbClient client, String listenerId) {
        ShowListenerResponse showListenerResponse = new ShowListenerResponse();
        try {
            showListenerResponse = client.showListener(new ShowListenerRequest()
                    .withListenerId(listenerId));
            logger.info(showListenerResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[ELB Listener]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[ELB Listener]RequestId: " + e.getRequestId());
            logger.error("[ELB Listener]ErrorCode: " + e.getErrorCode());
            logger.error("[ELB Listener]ErrorMsg: " + e.getErrorMsg());
        }
        return showListenerResponse.getListener();
    }

    /**
     * Querying ELB pool Entity according to poolId By Huaweicloud API
     * @param client Specifies the ELB client.
     * @param poolId The id of ELB pool
     * @return The Object entity of ELB pool
     */
    public PoolResp getPoolById(ElbClient client, String poolId) {
        ShowPoolResponse showPoolResponse = new ShowPoolResponse();
        try {
            showPoolResponse = client.showPool(new ShowPoolRequest()
                    .withPoolId(poolId));
            logger.info(showPoolResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[ELB Pool]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[ELB Pool]RequestId: " + e.getRequestId());
            logger.error("[ELB Pool]ErrorCode: " + e.getErrorCode());
            logger.error("[ELB Pool]ErrorMsg: " + e.getErrorMsg());
        }
        return showPoolResponse.getPool();
    }

    /**
     * Querying ELB member Entity according to poolId and memberId By Huaweicloud API
     * @param client Specifies the ELB client.
     * @param poolId The id of ELB pool
     * @param memberId The id of ELB member
     * @return The Object entity of ELB member
     */
    public MemberResp getMemberById(ElbClient client, String poolId, String memberId) {
        ShowMemberResponse showMemberResponse = new ShowMemberResponse();
        try {
            showMemberResponse = client.showMember(new ShowMemberRequest()
                    .withPoolId(poolId)
                    .withMemberId(memberId));
            logger.info(showMemberResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[ELB Pool]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[ELB Pool]RequestId: " + e.getRequestId());
            logger.error("[ELB Pool]ErrorCode: " + e.getErrorCode());
            logger.error("[ELB Pool]ErrorMsg: " + e.getErrorMsg());
        }
        return showMemberResponse.getMember();
    }

    /**
     * Querying ELB member Entity according to monitorId By Huaweicloud API
     * @param client
     * @param monitorId
     * @return
     */
    public HealthmonitorResp getHealthMonitor(ElbClient client, String monitorId) {
        ShowHealthmonitorsResponse showHealthmonitorsResponse = new ShowHealthmonitorsResponse();
        try {
            showHealthmonitorsResponse = client.showHealthmonitors(new ShowHealthmonitorsRequest()
                    .withHealthmonitorId(monitorId));
            logger.info(showHealthmonitorsResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[ELB Health Check]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[ELB Health Check]RequestId: " + e.getRequestId());
            logger.error("[ELB Health Check]ErrorCode: " + e.getErrorCode());
            logger.error("[ELB Health Check]ErrorMsg: " + e.getErrorMsg());
        }
        return showHealthmonitorsResponse.getHealthmonitor();
    }
}
