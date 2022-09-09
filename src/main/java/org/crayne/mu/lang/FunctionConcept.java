package org.crayne.mu.lang;

import org.crayne.mu.parsing.parser.ValueParser;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class FunctionConcept {

    private final String name;
    private final Datatype returnType;
    private final HashSet<FunctionDefinition> definitions;

    public FunctionConcept(@NotNull final String name, @NotNull final Datatype returnType) {
        this.name = name;
        this.returnType = returnType;
        this.definitions = new HashSet<>();
    }

    public final void addDefinition(@NotNull final FunctionDefinition def) {
        definitions.add(def);
    }

    public HashSet<FunctionDefinition> definitions() {
        return definitions;
    }

    public String name() {
        return name;
    }

    public Optional<FunctionDefinition> definitionByParameters(@NotNull final List<FunctionParameter> parameters) {
        for (@NotNull final FunctionDefinition def : definitions) {
            if (FunctionParameter.equalParams(def.parameters(), parameters)) return Optional.of(def);
        }
        return Optional.empty();
    }

    public Optional<FunctionDefinition> definitionByCallParameters(@NotNull final List<ValueParser.TypedNode> parameters) {
        for (@NotNull final FunctionDefinition def : definitions) {
            final List<FunctionParameter> defParams = def.parameters();
            if (defParams.size() != parameters.size()) continue;

            boolean equalParams = true;
            for (int i = 0; i < defParams.size(); i++) {
                final FunctionParameter defParam = defParams.get(i);
                final ValueParser.TypedNode callParam = parameters.get(i);
                if (callParam.type() == null) return Optional.empty();

                if (!callParam.type().equals(defParam.type())) {
                    equalParams = false;
                    break;
                }
            }
            if (equalParams) return Optional.of(def);
        }
        return Optional.empty();
    }

    public boolean isDefined(@NotNull final List<FunctionParameter> withParams) {
        return definitionByParameters(withParams).isPresent();
    }

    public boolean equals(@NotNull final FunctionConcept other) {
        return other.name.equals(name) && returnType.equals(other.returnType);
    }

    @Override
    public String toString() {
        return "FunctionConcept{" +
                "name='" + name + '\'' +
                ", returnType=" + returnType +
                ", definitions=" + definitions +
                '}';
    }
}
