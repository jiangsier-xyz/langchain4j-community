package dev.langchain4j.community.model.dashscope.spi;

import dev.langchain4j.community.model.dashscope.QwenEmbeddingModel;

import java.util.function.Supplier;

/**
 * A factory for building {@link QwenEmbeddingModel.QwenEmbeddingModelBuilder} instances.
 */
public interface QwenEmbeddingModelBuilderFactory extends Supplier<QwenEmbeddingModel.QwenEmbeddingModelBuilder> {
}
