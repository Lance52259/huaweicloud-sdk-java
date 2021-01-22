package com.huaweicloud.sdk.test.elb;

import com.huaweicloud.sdk.core.exception.ClientRequestException;
import com.huaweicloud.sdk.elb.v2.ElbClient;
import com.huaweicloud.sdk.elb.v2.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElbDelete {
    private static final Logger logger = LoggerFactory.getLogger(ElbDelete.class);

    ElbCheck elbCheck = new ElbCheck();

    /**
     * Delete Elastic Load Balancer according to loadBalancerId by Huaweicloud API.
     * @param client The client of ELB
     * @param loadBalancerId The id of Elastic Load Balancer
     * @throws InterruptedException
     */
    public void deleteLoadBalancer(ElbClient client, String loadBalancerId) throws InterruptedException {
        try {
            DeleteLoadbalancerResponse deleteLoadbalancerResponse = client.deleteLoadbalancer(new DeleteLoadbalancerRequest()
                    .withLoadbalancerId(loadBalancerId));
            logger.info(deleteLoadbalancerResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Elastic Load Balancer]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Elastic Load Balancer]RequestId: " + e.getRequestId());
            logger.error("[Elastic Load Balancer]ErrorCode: " + e.getErrorCode());
            logger.error("[Elastic Load Balancer]ErrorMsg: " + e.getErrorMsg());
        }
        if (!elbCheck.checkLoadBalancerInActive(client, false, loadBalancerId, 3, 3, 60)) {
            logger.error("[Error]Elastic Load Balancer delete failed.");
        }
    }

    /**
     * Delete ELB Listener according to listenerId by Huaweicloud API.
     * @param client The client of ELB
     * @param listenerId The id of ELB Listener
     * @throws InterruptedException
     */
    public void deleteListener(ElbClient client, String listenerId) throws InterruptedException {
        try {
            DeleteListenerResponse deleteListenerResponse = client.deleteListener(new DeleteListenerRequest()
                    .withListenerId(listenerId));
            logger.info(deleteListenerResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[ELB Listener]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[ELB Listener]RequestId: " + e.getRequestId());
            logger.error("[ELB Listener]ErrorCode: " + e.getErrorCode());
            logger.error("[ELB Listener]ErrorMsg: " + e.getErrorMsg());
        }
        if (!elbCheck.checkListenerInActive(client, false, listenerId, 3, 3, 60)) {
            logger.error("[Error]ELB Listener delete failed.");
        }
    }

    /**
     * Delete ELB Pool according to poolId by Huaweicloud API.
     * @param client The client of ELB
     * @param poolId The id of ELB Listener
     * @throws InterruptedException
     */
    public void deletePool(ElbClient client, String poolId)  throws InterruptedException {
        try {
            DeletePoolResponse deletePoolResponse = client.deletePool(new DeletePoolRequest()
                    .withPoolId(poolId));
            logger.info(deletePoolResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[ELB Pool]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[ELB Pool]RequestId: " + e.getRequestId());
            logger.error("[ELB Pool]ErrorCode: " + e.getErrorCode());
            logger.error("[ELB Pool]ErrorMsg: " + e.getErrorMsg());
        }
        if (!elbCheck.checkPoolInActive(client, false, poolId, 3, 3, 60)) {
            logger.error("[Error]ELB Pool delete failed.");
        }
    }

    /**
     * Delete ELB Member according to poolId and memberId byHuaweicloud API.
     * @param client The client of LEB
     * @param poolId The id of ELB pool
     * @param memberId The id of ELB member
     * @throws InterruptedException
     */
    public void deleteMember(ElbClient client, String poolId, String memberId) throws InterruptedException {
        try {
            DeleteMemberResponse deleteMemberResponse = client.deleteMember(new DeleteMemberRequest()
                    .withPoolId(poolId)
                    .withMemberId(memberId));
            logger.info(deleteMemberResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[ELB Member]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[ELB Member]RequestId: " + e.getRequestId());
            logger.error("[ELB Member]ErrorCode: " + e.getErrorCode());
            logger.error("[ELB Member]ErrorMsg: " + e.getErrorMsg());
        }
        if (!elbCheck.checkMemberInActive(client, false, poolId, memberId, 3, 3, 60)) {
            logger.error("[Error]ELB Member delete failed.");
        }
    }
}
