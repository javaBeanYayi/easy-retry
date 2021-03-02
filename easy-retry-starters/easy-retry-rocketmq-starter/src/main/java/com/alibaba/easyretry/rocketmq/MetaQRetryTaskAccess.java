package com.alibaba.easyretry.rocketmq;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.easyretry.common.access.RetryTaskAccess;
import com.alibaba.easyretry.common.entity.RetryTask;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Created by wuhao on 2021/3/2.
 */
public class MetaQRetryTaskAccess implements RetryTaskAccess {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Value("${demo.rocketmq.topic}")
    private String springTopic;

    @Override
    public boolean saveRetryTask(RetryTask retryTask) {
        SendResult sendResult = rocketMQTemplate.syncSend(springTopic, retryTask);
        return true;
    }

    @Override
    public boolean handlingRetryTask(RetryTask retryTask) {
        return false;
    }

    @Override
    public boolean finishRetryTask(RetryTask retryTask) {
        return false;
    }

    @Override
    public boolean stopRetryTask(RetryTask retryTask) {
        return false;
    }

    @Override
    public List<RetryTask> listAvailableTasks(String namespace, Long lastId) {
        return null;
    }
}
