package com.huaweicloud.sdk.test.vpc;

import com.huaweicloud.sdk.test.Application;
import com.huaweicloud.sdk.vpc.v2.VpcClient;
import com.huaweicloud.sdk.vpc.v2.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class VpcCheck {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    VpcRead vpcRead = new VpcRead();

    public void delay(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    public boolean checkVpcInActive(VpcClient client, String vpcId, int delayTime, int minTimeout, int maxTimeout, int continuousTargetOccurence) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;
        int targetOccurence = 0;

        if(continuousTargetOccurence == 0) {
            continuousTargetOccurence = 1;
        }

        while(true) {
            Vpc vpc = vpcRead.getVpcById(client, vpcId);
            if(vpc != null && vpc.getStatus().getValue().equals("OK")) {
                targetOccurence++;
                if (targetOccurence >= continuousTargetOccurence) {
                    return true;
                }
            }
            if(targetOccurence == 0) {
                wait = wait * 2;
            }
            delay(wait);
            long timeCost = System.currentTimeMillis() - startTime;
            if(timeCost < minTimeout*1000) {
                delay((int)(minTimeout * 1000 - timeCost));
            }else if(timeCost > maxTimeout*1000) {
                return false;
            }
        }
    }

    public boolean checkSubnetInActive(VpcClient client, String subnetId, int delayTime, int minTimeout, int maxTimeout, int continuousTargetOccurence) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;
        int targetOccurence = 0;

        if(continuousTargetOccurence == 0) {
            continuousTargetOccurence = 1;
        }

        while(true) {
            Subnet subnet = vpcRead.getSubnetById(client, subnetId);
            if(subnet != null && subnet.getStatus().getValue().equals("ACTIVE")) {
                targetOccurence++;
                if (targetOccurence >= continuousTargetOccurence) {
                    return true;
                }
            }
            if(targetOccurence == 0) {
                wait = wait * 2;
            }
            delay(wait);
            long timeCost = System.currentTimeMillis() - startTime;
            if(timeCost < minTimeout*1000) {
                delay((int)(minTimeout * 1000 - timeCost));
            }else if(timeCost > maxTimeout*1000) {
                return false;
            }
        }
    }

    public boolean checkSecurityGroupExist(VpcClient client, String securityGroupId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            SecurityGroup securityGroup = vpcRead.getSecurityGroupById(client, securityGroupId);
            if(securityGroup != null) {
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

    public boolean checkSecurityGroupRuleExist(VpcClient client, String securityGroupRuleId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            SecurityGroupRule securityGroupRule = vpcRead.getSecurityGroupRuleById(client, securityGroupRuleId);
            if(securityGroupRule != null) {
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

    //******************************************************************************************************************
    public boolean checkVpcDelete(VpcClient client, String vpcId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            Vpc vpc = vpcRead.getVpcById(client, vpcId);
            if(vpc == null) {
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

    public boolean checkSubnetDelete(VpcClient client, String vpcId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            Subnet subnet = vpcRead.getSubnetById(client, vpcId);
            if(subnet == null) {
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

    public boolean checkSecurityGroupDelete(VpcClient client, String securityGroupId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            SecurityGroup securityGroup = vpcRead.getSecurityGroupById(client, securityGroupId);
            if(securityGroup == null) {
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

    public boolean checkSecurityGroupRuleDelete(VpcClient client, String securityGroupRuleId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            SecurityGroupRule securityGroupRule = vpcRead.getSecurityGroupRuleById(client, securityGroupRuleId);
            if(securityGroupRule == null) {
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
}
