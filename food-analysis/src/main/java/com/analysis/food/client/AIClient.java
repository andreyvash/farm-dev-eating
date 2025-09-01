package com.analysis.food.client;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AIClient {

    private final ChatClient chatClient;
    private final OpenAiChatModel chatModel;

    @Autowired
    public AIClient(ChatClient.Builder chatClientBuilder, OpenAiChatModel chatModel) {
        this.chatClient = chatClientBuilder.build();
        this.chatModel = chatModel;
    }

    public String generation(String userInput) {
        return this.chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }

    public Map<String,String>  generationGrok(String userInput) {
        return Map.of("generation", this.chatModel.call(userInput));
    }
}
