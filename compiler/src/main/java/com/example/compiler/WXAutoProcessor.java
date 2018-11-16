package com.example.compiler;

import com.example.annotation.WXEntryGenerator;
import com.google.auto.service.AutoService;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;


@AutoService(Processor.class)
public class WXAutoProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationNames = new LinkedHashSet<>();
        Set<Class<? extends Annotation>> annotations = getsupportAnnotations();
        for (Class<? extends Annotation> annotation : annotations) {
            annotationNames.add(annotation.getCanonicalName());
        }
        return annotationNames;
    }

    /**
     * 构建支持的注解集合
     *
     * @return
     */
    private Set<Class<? extends Annotation>> getsupportAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(WXEntryGenerator.class);
        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        generateEntry(roundEnvironment);
        return true;
    }

    private void generateEntry(RoundEnvironment roundEnvironment) {
        EntryVisotor entryVisotor = new EntryVisotor();
        entryVisotor.setFiler(processingEnv.getFiler());
        scan(roundEnvironment,WXEntryGenerator.class,entryVisotor);
    }

    private void scan(RoundEnvironment roundEnvironment,
                      Class<WXEntryGenerator> clazz, EntryVisotor entryVisotor) {

        //遍历所有使用了clazz注解注释过的元素
        for (Element element : roundEnvironment.getElementsAnnotatedWith(clazz)) {
            //获取注解内部的属性集合
            List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
            for (AnnotationMirror mirror : annotationMirrors) {
                //获取注解内部的属性值
                Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = mirror.getElementValues();
                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {

                    ////将注解内部属性值交给注解属性解析器处理(EntryVisotor、ShareVisitor)
                    entry.getValue().accept(entryVisotor,null);
                }
            }
        }
    }


}
