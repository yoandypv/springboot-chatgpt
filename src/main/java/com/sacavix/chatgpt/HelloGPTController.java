package com.sacavix.chatgpt;

import io.github.flashvayne.chatgpt.dto.ChatRequest;
import io.github.flashvayne.chatgpt.dto.ChatResponse;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatboot")
public class HelloGPTController implements InitializingBean {

    @Autowired
    private ChatgptService chatgptService;

    @Override
    public void afterPropertiesSet() {
        System.out.println(" ===== Starting Chat GPT Boot ==== ");
    }

    @GetMapping("/chat")
    public String chatWith(@RequestParam String message) {
        System.out.println(message);
        return chatgptService.sendMessage(message);
    }

    @GetMapping("/prompt")
    public ChatResponse prompt(@RequestParam String message) {

        //The maximum number of tokens to generate in the completion.The token count of your prompt plus max_tokens cannot exceed the model's context length. Most models have a context length of 2048 tokens (except for the newest models, which support 4096).
        Integer maxTokens = 300;

        // GPT-3 models can understand and generate natural language. We offer four main models with different levels of power suitable for different tasks. Davinci is the most capable model, and Ada is the fastest.
        String model = "text-davinci-003";

        // What sampling temperature to use. Higher values means the model will take more risks. Try 0.9 for more creative applications, and 0 (argmax sampling) for ones with a well-defined answer.We generally recommend altering this or top_p but not both.
        Double temperature = 0.5;

        //An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered.We generally recommend altering this or temperature but not both.
        Double topP = 1.0;

        ChatRequest chatRequest = new ChatRequest(model, message, maxTokens,temperature,topP);
        ChatResponse res =  chatgptService.sendChatRequest(chatRequest);
        System.out.println("Response was: " + res.toString());
        return res;

    }
}
