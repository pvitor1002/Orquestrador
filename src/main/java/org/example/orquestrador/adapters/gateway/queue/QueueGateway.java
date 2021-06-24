package org.example.orquestrador.adapters.gateway.queue;

import org.example.orquestrador.adapters.event.entity.Request;
import org.example.orquestrador.adapters.gateway.channel.GatewayChannels;
import org.example.orquestrador.adapters.gateway.filters.IntegrationFlowDefinitions;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;

@MessagingGateway
public interface QueueGateway {

    @Gateway(requestChannel = IntegrationFlowDefinitions.HANDLER_FLOW, replyChannel = GatewayChannels.RESPONSE, replyTimeout = 8000)
    String handle(@Payload Request payload);

    @Gateway(requestChannel = GatewayChannels.RESPONSE)
    void comunicacaoLateral(@Payload Message<byte[]> payload);
}
