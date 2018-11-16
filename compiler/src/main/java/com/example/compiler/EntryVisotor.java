package com.example.compiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

public class EntryVisotor extends SimpleAnnotationValueVisitor7<Void,Void> {

    /**
     * 需要遍历的
     */
    private Filer mFiler;

    /**
     * 包名
     */
    private String packageName;


    public void setFiler(Filer filer) {
        mFiler = filer;
    }

    @Override
    public Void visitString(String s, Void aVoid) {
        packageName = s;
        return aVoid;
    }

    @Override
    public Void visitType(TypeMirror typeMirror, Void aVoid) {

        generatorJavaCode(typeMirror);
        return aVoid;
    }

    private void generatorJavaCode(TypeMirror typeMirror) {
        TypeSpec wxEntry = TypeSpec.classBuilder("WXEntryActivity")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(TypeName.get(typeMirror))
                .build();

        JavaFile javaFile = JavaFile.builder(packageName + ".wxapi", wxEntry)
                .addFileComment("微信登录入口")
                .build();


        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
