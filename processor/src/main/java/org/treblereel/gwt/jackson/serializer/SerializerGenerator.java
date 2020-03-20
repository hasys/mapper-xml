package org.treblereel.gwt.jackson.serializer;

import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.google.auto.common.MoreTypes;
import org.treblereel.gwt.jackson.api.XMLSerializationContext;
import org.treblereel.gwt.jackson.api.XMLSerializer;
import org.treblereel.gwt.jackson.api.ser.bean.AbstractBeanXMLSerializer;
import org.treblereel.gwt.jackson.api.ser.bean.BeanPropertySerializer;
import org.treblereel.gwt.jackson.context.GenerationContext;
import org.treblereel.gwt.jackson.generator.AbstractGenerator;
import org.treblereel.gwt.jackson.logger.TreeLogger;

/**
 * @author Dmitrii Tikhomirov
 * Created by treblereel 3/18/20
 */
public class SerializerGenerator extends AbstractGenerator {

    public SerializerGenerator(GenerationContext context, TreeLogger logger) {
        super(context, logger.branch(TreeLogger.INFO, "Serializers generation started"));
    }

    @Override
    protected String getMapperName(TypeElement type) {
        return context.getTypeUtils().serializerName(type.asType());
    }

    @Override
    protected void configureClassType(TypeElement type) {
        cu.addImport(XMLSerializationContext.class);
        cu.addImport(XMLSerializer.class);
        cu.addImport(AbstractBeanXMLSerializer.class);
        cu.addImport(BeanPropertySerializer.class);
        cu.addImport(XMLSerializer.class);
        cu.addImport(type.getQualifiedName().toString());

        declaration.getExtendedTypes().add(new ClassOrInterfaceType()
                                                   .setName(AbstractBeanXMLSerializer.class.getSimpleName())
                                                   .setTypeArguments(new ClassOrInterfaceType().setName(type.getSimpleName().toString())));
    }

    @Override
    protected void getType(TypeElement type) {
        declaration.addMethod("getSerializedType", Modifier.Keyword.PUBLIC)
                .addAnnotation(Override.class)
                .setType(Class.class)
                .getBody().ifPresent(body -> body.addStatement(new ReturnStmt(
                new FieldAccessExpr(
                        new NameExpr(type.getSimpleName().toString()), "class"))));
    }

    @Override
    protected void init(TypeElement type) {
        List<VariableElement> fields = getFields(type);
        MethodDeclaration initSerializers = declaration.addMethod("initSerializers", Modifier.Keyword.PROTECTED);
        initSerializers.addAnnotation(Override.class)
                .setType(BeanPropertySerializer[].class)
                .getBody().ifPresent(body -> processInitSerializersMethodBody(body, type, fields));
    }

    private void processInitSerializersMethodBody(BlockStmt body, TypeElement type, List<VariableElement> fields) {
        addBeanPropertySerializerDeclaration(body, fields);
        for (int i = 0; i < fields.size(); i++) {
            addBeanPropertySerializer(body, type, fields.get(i), i);
        }

        body.addStatement(new ReturnStmt(new NameExpr("result")));
    }

    private void addBeanPropertySerializerDeclaration(BlockStmt body, List<VariableElement> fields) {
        VariableDeclarator result = new VariableDeclarator();
        result.setType("BeanPropertySerializer[]");
        result.setName("result");
        result.setInitializer("new BeanPropertySerializer[" + fields.size() + "]");
        ExpressionStmt expressionStmt = new ExpressionStmt();
        VariableDeclarationExpr variableDeclarationExpr = new VariableDeclarationExpr();

        expressionStmt.setExpression(variableDeclarationExpr);
        variableDeclarationExpr.getVariables().add(result);
        body.addStatement(variableDeclarationExpr);
    }

    private void addBeanPropertySerializer(BlockStmt body, TypeElement type, VariableElement variableElement, int i) {
        ObjectCreationExpr beanProperty = new ObjectCreationExpr();
        ClassOrInterfaceType beanType = new ClassOrInterfaceType()
                .setName(BeanPropertySerializer.class.getSimpleName());

        beanProperty.setType(beanType);
        beanProperty.addArgument(new StringLiteralExpr(variableElement.getSimpleName().toString()));
        setTypeParams(type, variableElement, beanType);

        body.addStatement(new AssignExpr().setTarget(
                new ArrayAccessExpr(new NameExpr("result"),
                                    new IntegerLiteralExpr(i))).setValue(
                beanProperty));

        addMethods(beanProperty, type, variableElement);
    }

    private void setTypeParams(TypeElement type, VariableElement variableElement, ClassOrInterfaceType beanType) {
        NodeList<Type> typeArguments = new NodeList<>();
        typeArguments.add(new ClassOrInterfaceType().setName(type.getSimpleName().toString()));

        String fieldType;
        if (variableElement.asType().getKind().isPrimitive()) {
            fieldType = typeUtils.wrapperType(variableElement.asType());
        } else {
            fieldType = typeUtils.toTypeElement(variableElement.asType()).toString();
        }

        ClassOrInterfaceType interfaceType = new ClassOrInterfaceType();
        interfaceType.setName(fieldType);

        if (variableElement.asType() instanceof DeclaredType) {
            if (!((DeclaredType) variableElement.asType()).getTypeArguments().isEmpty()) {
                NodeList<Type> types = new NodeList<>();
                ((DeclaredType) variableElement.asType()).getTypeArguments()
                        .forEach(param -> types.add(new ClassOrInterfaceType().setName(param.toString())));
                interfaceType.setTypeArguments(types);
            }
        }
        typeArguments.add(interfaceType);
        beanType.setTypeArguments(typeArguments);
    }

    private void addMethods(ObjectCreationExpr beanProperty, TypeElement bean, VariableElement field) {
        NodeList<BodyDeclaration<?>> anonymousClassBody = new NodeList<>();
        beanProperty.setAnonymousClassBody(anonymousClassBody);

        newSerializer(anonymousClassBody, field.asType());
        getValue(anonymousClassBody, bean, field);
    }

    private void newSerializer(NodeList<BodyDeclaration<?>> anonymousClassBody, TypeMirror type) {
        MethodDeclaration method = new MethodDeclaration();
        method.setModifiers(Modifier.Keyword.PROTECTED);
        method.addAnnotation(Override.class);
        method.setName("newSerializer");
        method.setType(new ClassOrInterfaceType().setName("XMLSerializer<?>"));

        method.getBody().get().addAndGetStatement(new ReturnStmt().setExpression(getSerializerExpression(type)));
        anonymousClassBody.add(method);
    }

    private void getValue(NodeList<BodyDeclaration<?>> anonymousClassBody, TypeElement bean, VariableElement field) {
        MethodDeclaration method = new MethodDeclaration();
        method.setModifiers(Modifier.Keyword.PUBLIC);
        method.addAnnotation(Override.class);
        method.setName("getValue");
        method.addParameter(new ClassOrInterfaceType().setName(bean.getSimpleName().toString()), "bean");
        method.addParameter(XMLSerializationContext.class.getSimpleName(), "ctx");
        method.setType(new ClassOrInterfaceType().setName(typeUtils.wrapperType(field.asType())));
        method.getBody().get().addAndGetStatement(new ReturnStmt(new MethodCallExpr(new NameExpr("bean"), typeUtils.getGetter(field).getSimpleName().toString())));
        anonymousClassBody.add(method);
    }

    private Expression getSerializerExpression(TypeMirror type) {
        if (typeUtils.isBasicType(type)) {
            return new MethodCallExpr(
                    new NameExpr(context.getTypeRegistry()
                                         .getSerializer(context.getProcessingEnv().getTypeUtils().erasure(type)).toString()), "getInstance");
        } else {
            if (context.getTypeRegistry().get(context.getProcessingEnv()
                                                      .getTypeUtils().erasure(type).toString()) == null) {
                return new ObjectCreationExpr().setType(new ClassOrInterfaceType()
                                                                .setName(typeUtils.canonicalSerializerName(typeUtils.getPackage(type), type)));
            } else {
                TypeElement serializer = context.getTypeRegistry().getSerializer(context.getProcessingEnv().getTypeUtils().erasure(type));

                MethodCallExpr method = new MethodCallExpr(
                        new NameExpr(serializer.getQualifiedName().toString()), "newInstance");
                if (typeUtils.isCollection(type)) {
                    MoreTypes.asDeclared(type).getTypeArguments().forEach(param -> {
                        method.addArgument(getSerializerExpression(param));
                    });
                }
                return method;
            }
        }
    }
}