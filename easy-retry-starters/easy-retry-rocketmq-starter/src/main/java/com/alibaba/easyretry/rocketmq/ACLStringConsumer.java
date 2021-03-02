/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.easyretry.rocketmq;

import com.alibaba.easyretry.common.RetryConfiguration;
import com.alibaba.easyretry.common.RetryContext;
import com.alibaba.easyretry.common.RetryContext.RetryContextBuilder;
import com.alibaba.easyretry.common.RetryExecutor;
import com.alibaba.easyretry.common.constant.enums.HandleResultEnum;
import com.alibaba.easyretry.common.entity.RetryTask;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * RocketMQMessageListener
 */
@Service
@RocketMQMessageListener(
    topic = "normal_topic_define_in_Aliware_MQ",
    consumerGroup = "group_define_in_Aliware_MQ"
    //accessKey = "AK" // It will read by `rocketmq.consumer.access-key` key
    //secretKey = "SK" // It will read by `rocketmq.consumer.secret-key` key
)
@Slf4j
public class ACLStringConsumer implements RocketMQListener<RetryTask> {

    private static final Integer MAX_QUEUE_SIZE = 2000;

    private RetryConfiguration retryConfiguration;

    private RetryExecutor retryExecutor;

    @Override
    public void onMessage(RetryTask message) {
        RetryContext retryContext = new RetryContextBuilder(retryConfiguration, message).buildArgs()
            .buildExecutor().buildMethod()
            .buildRetryArgSerializer().buildStopStrategy().buildWaitStrategy().buildRetryTask()
            .buildMaxRetryTimes().buildOnFailureMethod().buildPriority(0L).build();
        HandleResultEnum handleResult = retryExecutor.doExecute(retryContext);
    }
}
