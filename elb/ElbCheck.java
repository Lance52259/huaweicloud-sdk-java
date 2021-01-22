package com.huaweicloud.sdk.test.elb;

import com.huaweicloud.sdk.elb.v2.ElbClient;
import com.huaweicloud.sdk.elb.v2.model.*;

public class ElbCheck {
    ElbRead elbRead = new ElbRead();

    public void delay(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    /**
     * Checkout status of Elastic Load Balancer(Exist or not)
     * @param client The client of ELB
     * @param loadBalancerId The id of Elastic Load Balance
     * @param delayTime At first, thread will sleep some time to wait entity create or update
     * @param minTimeout The min time of the status waiting
     * @param maxTimeout The max time of the status waiting
     * @return The result of status check
     * @throws InterruptedException
     */
    public boolean checkLoadBalancerInActive(ElbClient client, Boolean statusEnable, String loadBalancerId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            LoadbalancerResp loadbalancerResp = elbRead.getLoadBalancerById(client, loadBalancerId);
            if((loadbalancerResp != null) == statusEnable) {
                return true;
            }
            wait = wait * 2;
            delay(wait);
            long timeCost = System.currentTimeMillis() - startTime;
            if(timeCost < minTimeout*1000) {
                delay((int)(minTimeout * 1000 - timeCost));
            }else if(timeCost > maxTimeout*1000) {
                return false;
            }
        }
    }

    public boolean checkListenerInActive(ElbClient client, Boolean statusEnable, String listenerId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            ListenerResp listenerResp = elbRead.getListenerById(client, listenerId);
            if((listenerResp != null) == statusEnable) {
                return true;
            }
            wait = wait * 2;
            delay(wait);
            long timeCost = System.currentTimeMillis() - startTime;
            if(timeCost < minTimeout*1000) {
                delay((int)(minTimeout * 1000 - timeCost));
            }else if(timeCost > maxTimeout*1000) {
                return false;
            }
        }
    }

    public boolean checkPoolInActive(ElbClient client, Boolean statusEnable, String poolId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            PoolResp poolResp = elbRead.getPoolById(client, poolId);
            if((poolResp != null) == statusEnable) {
                return true;
            }
            wait = wait * 2;
            delay(wait);
            long timeCost = System.currentTimeMillis() - startTime;
            if(timeCost < minTimeout*1000) {
                delay((int)(minTimeout * 1000 - timeCost));
            }else if(timeCost > maxTimeout*1000) {
                return false;
            }
        }
    }

    public boolean checkMemberInActive(ElbClient client, Boolean statusEnable, String poolId, String memberId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            MemberResp memberResp = elbRead.getMemberById(client, poolId, memberId);
            if((memberResp != null) == statusEnable) {
                return true;
            }
            wait = wait * 2;
            delay(wait);
            long timeCost = System.currentTimeMillis() - startTime;
            if(timeCost < minTimeout*1000) {
                delay((int)(minTimeout * 1000 - timeCost));
            }else if(timeCost > maxTimeout*1000) {
                return false;
            }
        }
    }

    public boolean checkHealthMonitorInActive(ElbClient client, Boolean statusEnable, String monitorId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime * 1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while (true) {
            HealthmonitorResp healthmonitorResp = elbRead.getHealthMonitor(client, monitorId);
            if ((healthmonitorResp != null) == statusEnable) {
                return true;
            }
            wait = wait * 2;
            delay(wait);
            long timeCost = System.currentTimeMillis() - startTime;
            if (timeCost < minTimeout * 1000) {
                delay((int) (minTimeout * 1000 - timeCost));
            } else if (timeCost > maxTimeout * 1000) {
                return false;
            }
        }
    }
}
