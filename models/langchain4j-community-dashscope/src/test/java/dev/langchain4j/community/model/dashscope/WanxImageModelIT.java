package dev.langchain4j.community.model.dashscope;

import static dev.langchain4j.community.model.dashscope.QwenTestHelper.apiKey;
import static dev.langchain4j.community.model.dashscope.QwenTestHelper.multimodalImageData;
import static org.assertj.core.api.Assertions.assertThat;

import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.output.Response;
import java.net.URI;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EnabledIfEnvironmentVariable(named = "DASHSCOPE_API_KEY", matches = ".+")
class WanxImageModelIT {

    Logger log = LoggerFactory.getLogger(WanxImageModelIT.class);

    @ParameterizedTest
    @MethodSource("dev.langchain4j.community.model.dashscope.WanxTestHelper#imageModelNameProvider")
    void simple_image_generation_works(String modelName) {
        WanxImageModel model =
                WanxImageModel.builder().apiKey(apiKey()).modelName(modelName).build();

        Response<Image> response = model.generate("Beautiful house on country side");

        URI remoteImage = response.content().url();
        log.info("Your remote image is here: {}", remoteImage);
        assertThat(remoteImage).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("dev.langchain4j.community.model.dashscope.WanxTestHelper#imageModelNameProvider")
    void simple_image_generation_works_by_customized_request(String modelName) {
        WanxImageModel model =
                WanxImageModel.builder().apiKey(apiKey()).modelName(modelName).build();

        model.setImageSynthesisParamCustomizer(builder -> {
            builder.extraInput("lora_index", "wanx1.4.6_textlora_jianzhi1_20240816");
            builder.extraInput("trigger_word", "papercut");
        });

        Response<Image> response = model.generate("Beautiful house on country side");

        URI remoteImage = response.content().url();
        log.info("Your remote image is here: {}", remoteImage);
        assertThat(remoteImage).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("dev.langchain4j.community.model.dashscope.WanxTestHelper#imageModelNameProvider")
    void simple_image_edition_works_by_url(String modelName) {
        WanxImageModel model =
                WanxImageModel.builder().apiKey(apiKey()).modelName(modelName).build();

        Image image = Image.builder()
                .url("https://help-static-aliyun-doc.aliyuncs.com/assets/img/zh-CN/2476628361/p335710.png")
                .build();

        Response<Image> response = model.edit(image, "Draw a parrot");

        URI remoteImage = response.content().url();
        log.info("Your remote image is here: {}", remoteImage);
        assertThat(remoteImage).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("dev.langchain4j.community.model.dashscope.WanxTestHelper#imageModelNameProvider")
    void simple_image_edition_works_by_data(String modelName) {
        WanxImageModel model =
                WanxImageModel.builder().apiKey(apiKey()).modelName(modelName).build();

        Image image = Image.builder()
                .base64Data(multimodalImageData())
                .mimeType("image/jpg")
                .build();

        Response<Image> response = model.edit(image, "Change the parrot's feathers with yellow");

        URI remoteImage = response.content().url();
        log.info("Your remote image is here: {}", remoteImage);
        assertThat(remoteImage).isNotNull();
    }
}
