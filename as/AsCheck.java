package com.huaweicloud.sdk.test.as;

import com.huaweicloud.sdk.as.v1.AsClient;

import com.huaweicloud.sdk.as.v1.model.ScalingConfiguration;
import com.huaweicloud.sdk.as.v1.model.ScalingGroupInstance;
import com.huaweicloud.sdk.as.v1.model.ScalingGroups;
import com.huaweicloud.sdk.as.v1.model.ScalingPolicyDetail;
import com.huaweicloud.sdk.ces.v1.CesClient;
import com.huaweicloud.sdk.ces.v1.model.MetricAlarms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AsCheck {
    private static final Logger logger = LoggerFactory.getLogger(AsCheck.class);

    AsRead asRead = new AsRead();

    public void delay(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    public boolean checkScalingGroupInActive(AsClient client, String asGroupId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            ScalingGroups scalingGroups = asRead.getScalingGroupById(client, asGroupId);
            if(scalingGroups != null) {
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

    public boolean checkScalingConfigInActive(AsClient client, String asConfigId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            ScalingConfiguration scalingConfiguration = asRead.getScalingConfigurationById(client, asConfigId);
            if(scalingConfiguration != null) {
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

    public boolean checkScalingPolicyInActive(AsClient client, String asGroupId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            ScalingPolicyDetail scalingPolicyDetail = asRead.getScalingPolicy(client, asGroupId);
            if(scalingPolicyDetail != null) {
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

    public boolean checkAlarmsInActive(CesClient client, String alarmId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            MetricAlarms metricAlarms = asRead.getMetricAlarmsById(client, alarmId);
            if(metricAlarms != null) {
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
    public boolean checkScalingGroupDelete(AsClient client, String asGroupId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            ScalingGroups scalingGroups = asRead.getScalingGroupById(client, asGroupId);
            if(scalingGroups == null) {
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

    public boolean checkScalingConfigDelete(AsClient client, String asConfigId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            ScalingConfiguration scalingConfiguration = asRead.getScalingConfigurationById(client, asConfigId);
            if(scalingConfiguration == null) {
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

    public boolean checkScalingPolicyDelete(AsClient client, String asGroupId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            ScalingPolicyDetail scalingPolicyDetail = asRead.getScalingPolicy(client, asGroupId);
            if(scalingPolicyDetail != null) {
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

    public boolean checkAlarmsDelete(CesClient client, String alarmId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            MetricAlarms metricAlarms = asRead.getMetricAlarmsById(client, alarmId);
            if(metricAlarms == null) {
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

    public boolean checkScalingInstanceDelete(AsClient client, String asGroupId, String instanceId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            List<ScalingGroupInstance> scalingGroupInstanceList = asRead.getInstancesListById(client, asGroupId);
            int count = 0;
            for(ScalingGroupInstance scalingGroupInstance : scalingGroupInstanceList) {
                if(scalingGroupInstance.getInstanceId().equals(instanceId)) {
                    break;
                }
                count ++;
            }
            if(count == scalingGroupInstanceList.size()) {
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

    public boolean checkAllScalingInstanceDelete(AsClient client, String asGroupId, int delayTime, int minTimeout, int maxTimeout) throws InterruptedException {
        delay(delayTime*1000);
        long startTime = System.currentTimeMillis();
        int wait = 100;

        while(true) {
            List<ScalingGroupInstance> scalingGroupInstanceList = asRead.getInstancesListById(client, asGroupId);
            if(scalingGroupInstanceList.size() == 0) {
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
