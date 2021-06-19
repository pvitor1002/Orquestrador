package org.example.orquestrador.adapters.controller.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.orquestrador.adapters.event.entity.Request;
import org.example.orquestrador.adapters.event.entity.Response;
import org.example.orquestrador.adapters.gateway.channel.GatewayChannels;
import org.example.orquestrador.adapters.gateway.queue.QueueGateway;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Configuration
@RestController
@EnableBinding({GatewayChannels.class})
@EnableIntegration
@IntegrationComponentScan
@RequiredArgsConstructor
public class OrquestradorClient {

    private UUID id;
    private final QueueGateway gateway;
    private final ObjectMapper mapper;

    @PostMapping("/transferencia")
    public ResponseEntity<Response> transferir(@RequestBody Request request){
        try {
            System.out.println("Iniciando transferencia");
            return ResponseEntity.ok()
                    .body(mapper.readValue(gateway.handle(request), Response.class));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }finally {
            System.out.println("aaa");
        }
    }

    @PostMapping("/response")
    public Response response(@RequestBody byte[] responseByte, @RequestHeader Map<String, String> headers){
        Response response = new Response();
        try {
            System.err.println("Entrando no comunicacao lateral");
            ObjectMapper mapper = new ObjectMapper();
            response = mapper.readValue(responseByte, Response.class);
            gateway.comunicacaoLateral(MessageBuilder.withPayload(responseByte).copyHeaders(headers).build());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }
}
