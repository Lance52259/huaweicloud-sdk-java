package com.huaweicloud.sdk.test.elb;

import com.huaweicloud.sdk.core.exception.ClientRequestException;
import com.huaweicloud.sdk.elb.v2.ElbClient;
import com.huaweicloud.sdk.elb.v2.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElbCreate {
    private static final Logger logger = LoggerFactory.getLogger(ElbCreate.class);

    ElbCheck elbCheck = new ElbCheck();
    ElbRead elbRead = new ElbRead();

    /**
     * Create a Elastic Loadbalancer by Huaweicloud API.
     * @param client Specifies the ELB client.
     * @param loadBalancerName The name of Elastic Load Balancer
     * @param description The description of Elastic Load Balancer
     * @param subnetId The id of VPC subnet
     * @param provider The provider of Elastic Load Balancer
     * @return The Object entity of Elastic Load Balancer
     * @throws InterruptedException
     */
    public LoadbalancerResp createLoadBalancer(ElbClient client, String loadBalancerName, String description,
                                               String subnetId, String provider) throws InterruptedException {
        CreateLoadbalancerResponse createLoadbalancerResponse = new CreateLoadbalancerResponse();

        try {
            createLoadbalancerResponse = client.createLoadbalancer(new CreateLoadbalancerRequest()
                    .withBody(new CreateLoadbalancerRequestBody()
                            .withLoadbalancer(new CreateLoadbalancerReq()
                                    .withName(loadBalancerName)
                                    .withDescription(description)
                                    .withVipSubnetId(subnetId)
                                    .withProvider(CreateLoadbalancerReq.ProviderEnum.VLB))));
            logger.info(createLoadbalancerResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[Elastic Load Balancer]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[Elastic Load Balancer]RequestId: " + e.getRequestId());
            logger.error("[Elastic Load Balancer]ErrorCode: " + e.getErrorCode());
            logger.error("[Elastic Load Balancer]ErrorMsg: " + e.getErrorMsg());
        }
        if (!elbCheck.checkLoadBalancerInActive(client, true, createLoadbalancerResponse.getLoadbalancer().getId(), 3, 3, 60)) {
            logger.error("[Error]Scaling Group delete failed.");
        }
        return createLoadbalancerResponse.getLoadbalancer();
    }

    /**
     * Create a listener of ELB by Huaweicloud API.
     * @param client Specifies the ELB client.
     * @param listenerName The Name of ELB listener
     * @param description The description of ELB listener
     * @param protocol The protocol of ELB listener, support 'UDP', 'TCP', 'HTTP' and 'TERMINATED_HTTPS'
     * @param protocolPort The port of protocol, valid values are 1 to 65535
     * @param loadBalancerId The id of ELB listener
     * @param poolId The pool ID of ELB
     * @return The Object entity of ELB listener
     * @throws InterruptedException
     */
    public ListenerResp createLoadBalancerListener(ElbClient client, String listenerName, String description,
            String protocol, int protocolPort, String loadBalancerId, String poolId) throws InterruptedException {
        CreateListenerResponse createListenerResponse = new CreateListenerResponse();
        try {
            createListenerResponse = client.createListener(new CreateListenerRequest()
                    .withBody(new CreateListenerRequestBody()
                            .withListener(new CreateListenerReq()
                                    .withName(listenerName)
                                    .withDescription(description)
                                    .withProtocol(CreateListenerReq.ProtocolEnum.valueOf(protocol))
                                    .withProtocolPort(protocolPort)
                                    .withLoadbalancerId(loadBalancerId)
                                    .withDefaultPoolId(poolId))));
            logger.info(createListenerResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[ELB Listener]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[ELB Listener]RequestId: " + e.getRequestId());
            logger.error("[ELB Listener]ErrorCode: " + e.getErrorCode());
            logger.error("[ELB Listener]ErrorMsg: " + e.getErrorMsg());
        }
        if (!elbCheck.checkListenerInActive(client, true, createListenerResponse.getListener().getId(), 3, 3, 60)) {
            logger.error("[Error]Scaling Group delete failed.");
        }
        return createListenerResponse.getListener();
    }

    /**
     * Create a ELB pool according to listenerId by Huaweicloud API.
     * @param client Specifies the ELB client.
     * @param poolName The name of pool
     * @param description The description of pool
     * @param protocol The description of pool, support 'UDP', 'TCP' and 'HTTP'
     * @param lbAlgorithm The algorithm of pool, support 'ROUND_ROBIN', 'LEAST_CONNECTIONS' and 'SOURCE_IP'
     * @param listenerId The id of listener
     * @return The Object entity of ELB pool
     * @throws InterruptedException
     */
    public PoolResp createPoolByListenerId(ElbClient client, String poolName, String description, String protocol,
                                         String lbAlgorithm, String listenerId) throws InterruptedException {
        CreatePoolResponse createPoolResponse = new CreatePoolResponse();
        try {
            createPoolResponse = client.createPool(new CreatePoolRequest()
                    .withBody(new CreatePoolRequestBody()
                            .withPool(new CreatePoolReq()
                                    .withName(poolName)
                                    .withDescription(description)
                                    .withProtocol(CreatePoolReq.ProtocolEnum.valueOf(protocol.toUpperCase()))
                                    .withLbAlgorithm(lbAlgorithm.toUpperCase())
                                    .withListenerId(listenerId)
                                    .withSessionPersistence(new SessionPersistence())))); //SessionPersistence set null means closed.
            logger.info(createPoolResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[ELB Pool]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[ELB Pool]RequestId: " + e.getRequestId());
            logger.error("[ELB Pool]ErrorCode: " + e.getErrorCode());
            logger.error("[ELB Pool]ErrorMsg: " + e.getErrorMsg());
        }
        if (!elbCheck.checkPoolInActive(client, true, createPoolResponse.getPool().getId(), 3, 3, 60)) {
            logger.error("[Error]ELB Pool delete failed.");
        }
        return createPoolResponse.getPool();
    }

    /**
     * Create a ELB pool according to loadbalancerId by Huaweicloud API.
     * @param client Specifies the ELB client.
     * @param poolName Specifies the name of the backend server group(pool)
     * @param description Specifies the description of the backend server group
     * @param protocol Specifies the protocol that the backend server group uses to receive requests, support 'UDP', 'TCP' and 'HTTP'
     * @param lbAlgorithm Specifies the ELB algorithm of the backend server group, support 'ROUND_ROBIN', 'LEAST_CONNECTIONS' and 'SOURCE_IP'
     * @param loadbalancerId Specifies the ID of ELB loadbalancer associated with the backend server group
     * @return The Object entity of ELB pool
     * @throws InterruptedException
     */
    public PoolResp createPoolByLoadbalancerId(ElbClient client, String poolName, String description, String protocol,
                                         String lbAlgorithm, String loadbalancerId) throws InterruptedException {
        CreatePoolResponse createPoolResponse = new CreatePoolResponse();
        try {
            createPoolResponse = client.createPool(new CreatePoolRequest()
                    .withBody(new CreatePoolRequestBody()
                            .withPool(new CreatePoolReq()
                                    .withName(poolName)
                                    .withDescription(description)
                                    .withProtocol(CreatePoolReq.ProtocolEnum.valueOf(protocol.toUpperCase()))
                                    .withLbAlgorithm(lbAlgorithm.toUpperCase())
                                    .withLoadbalancerId(loadbalancerId)
                                    .withSessionPersistence(new SessionPersistence())))); //SessionPersistence set null means closed.
            logger.info(createPoolResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[ELB Pool]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[ELB Pool]RequestId: " + e.getRequestId());
            logger.error("[ELB Pool]ErrorCode: " + e.getErrorCode());
            logger.error("[ELB Pool]ErrorMsg: " + e.getErrorMsg());
        }
        if (!elbCheck.checkPoolInActive(client, true, createPoolResponse.getPool().getId(), 3, 3, 60)) {
            logger.error("[Error]ELB Pool delete failed.");
        }
        return createPoolResponse.getPool();
    }

    /**
     * Create a ELB member according to poolId by Huaweicloud API.
     * @param client Specifies the ELB client.
     * @param poolId Specifies the ID of ELB pool.
     * @param memberName  Specifies the backend server(ELB member) name.
     * @param protocolPort  Specifies the port used by the backend server, the port number ranges form 1 to 65535.
     * @param ipAddress Specifies the floating ip address belongs VPC of backend server(belong to VPC subnet).
     * @param subnetId Specifies the ID of subnet where the backend server works.
     * @param weight Specifies the backend server weight.
     * @return The Object entity of ELB member
     * @throws InterruptedException
     */
    public MemberResp createMemberByPoolId(ElbClient client, String poolId, String memberName, String ipAddress, int protocolPort,
                                           String subnetId, int weight) throws InterruptedException {
        CreateMemberResponse createMemberResponse = new CreateMemberResponse();
        try {
            createMemberResponse = client.createMember(new CreateMemberRequest()
                    .withBody(new CreateMemberRequestBody()
                            .withMember(new CreateMemberReq()
                                    .withName(memberName)
                                    .withAddress(ipAddress)
                                    .withProtocolPort(protocolPort)
                                    .withSubnetId(subnetId)
                                    .withWeight(weight)))
                    .withPoolId(poolId));
            logger.info(createMemberResponse.toString());
        } catch (ClientRequestException e) {
            logger.error("[ELB Member]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[ELB Member]RequestId: " + e.getRequestId());
            logger.error("[ELB Member]ErrorCode: " + e.getErrorCode());
            logger.error("[ELB Member]ErrorMsg: " + e.getErrorMsg());
        }
        if (!elbCheck.checkMemberInActive(client, true, poolId, createMemberResponse.getMember().getId(), 3, 3, 60)) {
            logger.error("[Error]ELB Member delete failed.");
        }
        return createMemberResponse.getMember();
    }

    /**
     * Create a ELB health check(monitor) by Huaweicloud API.
     * @param client Specifies the ELB client.
     * @param monitorName Specifies the health check name.
     * @param healthDelay Specifies the maximum time between health checks in the unit of second.
     * @param maxRetries Specifies the number of consecutive health checks when the health check result of a backend server changes from OFF_LINE to ON_LINE.
     * @param poolId Specifies the ID of backend server group(pool).
     * @param timeout Specifies the health check timeout duration in the unit of second.
     * @param healthCheckType Specifies the health check protocol, valid values are 'TCP', 'UDP_CONNECT' and 'HTTP'.
     * @param monitorPort Specifies the health check port.
     * @param domainName The domain name of HTTP requests during the health check.
     *                   This parameter is valid only when the value of 'healthCheckType' is set to 'HTTP'.
     * @param urlPath The HTTP request path for the health check.
     *                This parameter is valid only when the value of 'healthCheckType' is set to 'HTTP'.
     * @param expectedCodes The expected HTTP status code.
     *                      This parameter is valid only when the value of 'healthCheckType' is set to 'HTTP'.
     * @param httpMethod The HTTP request method.
     *                   This parameter is valid only when the value of 'healthCheckType' is set to 'HTTP'.
     * @return The Object entity of ELB health check(monitor)
     */
    public HealthmonitorResp createHealthmonitor(ElbClient client, String monitorName, int healthDelay, int maxRetries,
                                                 String poolId, int timeout, String healthCheckType, int monitorPort,
                                                 String domainName, String urlPath, String expectedCodes, String httpMethod) throws InterruptedException {
        CreateHealthmonitorResponse createHealthMonitorResponse = new CreateHealthmonitorResponse();
        try {
            createHealthMonitorResponse = client.createHealthmonitor(new CreateHealthmonitorRequest()
                    .withBody(new CreateHealthmonitorRequestBody()
                            .withHealthmonitor(new CreateHealthmonitorReq()
                                    .withName(monitorName)
                                    .withDelay(healthDelay)
                                    .withMaxRetries(maxRetries)
                                    .withPoolId(poolId)
                                    .withTimeout(timeout)
                                    .withType(CreateHealthmonitorReq.TypeEnum.valueOf(healthCheckType))
                                    .withMonitorPort(monitorPort)
                                    .withDomainName(domainName)
                                    .withUrlPath(urlPath)
                                    .withExpectedCodes(expectedCodes)
                                    .withHttpMethod(httpMethod))));
        } catch (ClientRequestException e) {
            logger.error("[ELB Member]HttpStatusCode: " + e.getHttpStatusCode());
            logger.error("[ELB Member]RequestId: " + e.getRequestId());
            logger.error("[ELB Member]ErrorCode: " + e.getErrorCode());
            logger.error("[ELB Member]ErrorMsg: " + e.getErrorMsg());
        }
        if (!elbCheck.checkHealthMonitorInActive(client, true, createHealthMonitorResponse.getHealthmonitor().getId(), 3, 3, 60)) {
            logger.error("[Error]ELB Member delete failed.");
        }
        return createHealthMonitorResponse.getHealthmonitor();
    }
}
