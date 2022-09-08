package org.crayne.mu.runtime.lang;

import org.crayne.mu.lang.Enum;
import org.crayne.mu.lang.FunctionConcept;
import org.crayne.mu.lang.Module;
import org.crayne.mu.lang.Variable;
import org.crayne.mu.runtime.util.MuUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("unused")
public class RModule {

    private final String name;
    private final List<RVariable> globalModuleVariables;
    private final List<RModule> subModules;
    private final HashSet<REnum> enums;
    private final HashSet<RFunction> functions;

    public RModule(@NotNull final String name) {
        this.name = name;
        this.globalModuleVariables = new ArrayList<>();
        this.subModules = new ArrayList<>();
        this.enums = new HashSet<>();
        this.functions = new HashSet<>();
    }

    public RModule(@NotNull final String name, @NotNull final Collection<RVariable> globalVariables,
                   @NotNull final Collection<RModule> subModules, @NotNull final Collection<REnum> enums, Collection<RFunction> functions) {
        this.name = name;
        this.globalModuleVariables = new ArrayList<>(globalVariables);
        this.subModules = new ArrayList<>(subModules);
        this.enums = new HashSet<>(enums);
        this.functions = new HashSet<>(functions);
    }

    public String getName() {
        return name;
    }

    public List<RModule> getSubModules() {
        return subModules;
    }

    public HashSet<REnum> getEnums() {
        return enums;
    }

    public HashSet<RFunction> getFunctions() {
        return functions;
    }

    public List<RVariable> getGlobalModuleVariables() {
        return globalModuleVariables;
    }

    public static RModule of(@NotNull final Module m) {
        return new RModule(
                m.name(),
                globalVarsOf(m.moduleVariables()),
                submodulesOf(m.subModules()),
                enumsOf(m.enums()),
                functionsOf(m.functionConcepts())
        );
    }

    public static Set<RVariable> globalVarsOf(@NotNull final List<Variable> globalVars) {
        return MuUtil.unmodifiableSet(
                globalVars
                        .stream()
                        .map(RVariable::of)
        );
    }

    public static Set<RModule> submodulesOf(@NotNull final List<Module> submodules) {
        return MuUtil.unmodifiableSet(
                submodules
                        .stream()
                        .map(RModule::of)
        );
    }

    public static Set<REnum> enumsOf(@NotNull final HashSet<Enum> enums) {
        return MuUtil.unmodifiableSet(
                enums
                        .stream()
                        .map(REnum::of)
        );
    }

    public static Set<RFunction> functionsOf(@NotNull final HashSet<FunctionConcept> concepts) {
        return MuUtil.unmodifiableSet(
                concepts
                        .stream()
                        .map(FunctionConcept::definitions)
                        .flatMap(HashSet::stream)
                        .map(RFunction::of)
        );
    }

    @Override
    public String toString() {
        return "RModule{" +
                "name='" + name + '\'' +
                ", globalModuleVariables=" + globalModuleVariables +
                ", subModules=" + subModules +
                ", enums=" + enums +
                ", functions=" + functions +
                '}';
    }

}
