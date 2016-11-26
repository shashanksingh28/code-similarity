package edu.asu.ast.java;

import com.github.javaparser.ast.DocumentableNode;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.ReferenceType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Shashank Singh (shashank.h.singh@asu.edu) on 9/25/16.
 * This class represents a java function. An instance of this class can be considered as a vector
 * representation of the function, and different vectors can be compared to each other for checking
 * their cosine distance.
 */
public class ASTEnhanced {
    
	//--------- Syntactic features  ----------//

    // [return type, arg1 type, arg2 type, ..]
    ArrayList<String> paramTypes;   
    String returnType;
    
    HashMap<String, Integer> methodCalls;
    
    HashMap<String, Integer> constants;
    
    Set<String> annotations;
    
    HashMap<String, Integer> types;
    Set<String> expressions;
    Set<String> statements;
    
    // High level concepts we can extract
    Set<String> concepts;
    
    // Exceptions dealt with in this case, both declared and handled
    Set<String> exceptions;

    // ---------- Semantic Features --------- //
    
    // source as is
    String text;
    
    String name;
    String className;
    HashMap<String, Integer> variables;
    
    // JavaDoc documentation string for the method
    String javaDoc;

    // Comments within the method or above it which are not JavaDoc
    String comments;
    
    // ---------- Numeric Features --------- //
    boolean isEmpty;
    int lineCount;
    // This helps determine if the modifier is static
    // If it is static, the scope of a method does not contain class name
    int modifier;

    ASTEnhanced() {

        this.methodCalls = new HashMap<String, Integer>();
        this.concepts = new HashSet<String>();
        this.comments = null;
        this.paramTypes = new ArrayList<String>();
        this.variables = new HashMap<String, Integer>();
        this.constants = new HashMap<String, Integer>();
        this.types = new HashMap<String, Integer>();
        this.exceptions = new HashSet<String>();
        this.annotations = new HashSet<String>();
        this.expressions = new HashSet<String>();
        this.statements = new HashSet<String>();
    }

    public static String cleanDocumentation(String documentation){
        String processed = documentation.replaceAll("(\r\n|\n)"," ");
        
        // Remove @param and @return because they are common accross
        processed = processed.replaceAll("(@param|@return)", "").replaceAll("[,.*]", " ").replaceAll("\\s+", " "); 
        return processed.trim();
    }
    
    /**
     * The standard factory method to get an enhanced AST from a method
     * @param methodDec Method declaration object as per javaparser
     * @return An enhanced AST, which can be converted to a JSON
     */
    public void buildMethodAST(MethodDeclaration methodDec){
    	try{
                                    
            this.text = printAndExtractText(methodDec);
            
            // Get all thrown Exceptions
            for(ReferenceType type: methodDec.getThrows()){
            	this.exceptions.add(type.toString());
            }
            
            this.name = methodDec.getName();
            Node parent = methodDec.getParentNode();
            if (parent instanceof ClassOrInterfaceDeclaration){
                this.className = ((ClassOrInterfaceDeclaration) parent).getName();
            }
            this.modifier = methodDec.getModifiers();
            this.returnType = methodDec.getType().toString();

            for (Parameter param : methodDec.getParameters()) {
                this.paramTypes.add(param.getType().toString());
                incrementDictCount(this.variables, param.getName());
            }
            
            // Get Annotations
            for(AnnotationExpr annotExpr: methodDec.getAnnotations()){
            	this.annotations.add(annotExpr.getName().toString());
            }
            
            // If JavaDocs present, extract them
            if (methodDec.getJavaDoc() != null){
                this.javaDoc = cleanDocumentation(methodDec.getJavaDoc().getContent());
            }
            
            this.comments = extractContainedComments(methodDec);
            
            // These are in-line comments just before the method definitition
            if (methodDec.getComment() != null){
            	if (this.comments == null){
            		this.comments = "";
            	}
            	this.comments += methodDec.getComment().getContent(); 
            }
            
            // Recursively loop through child nodes and make a dictionary
            this.parseBody(methodDec.getBody());

        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void buildConstructorAST(ConstructorDeclaration constDec){
    	try{
            
            this.text = printAndExtractText(constDec);
            
            // Get all thrown Exceptions
            for(ReferenceType type: constDec.getThrows()){
            	this.exceptions.add(type.toString());
            }
            
            this.name = constDec.getName();
            Node parent = constDec.getParentNode();
            if (parent instanceof ClassOrInterfaceDeclaration){
                this.className = ((ClassOrInterfaceDeclaration) parent).getName();
            }
            this.modifier = constDec.getModifiers();
            this.returnType = this.className;

            for (Parameter param : constDec.getParameters()) {
                this.paramTypes.add(param.getType().toString());
                incrementDictCount(this.variables, param.getName());
            }
            
            // Get Annotations
            for(AnnotationExpr annotExpr: constDec.getAnnotations()){
            	this.annotations.add(annotExpr.getName().toString());
            }
            
            // If JavaDocs present, extract them
            if (constDec.getJavaDoc() != null){
                this.javaDoc = cleanDocumentation(constDec.getJavaDoc().getContent());
            }
            
            this.comments = extractContainedComments(constDec);
            
            // These are in-line comments just before the method definitition
            if (constDec.getComment() != null){
            	if (this.comments == null){
            		this.comments = "";
            	}
            	this.comments += constDec.getComment().getContent(); 
            }
            
            // Recursively loop through child nodes and make a dictionary
            this.parseBody(constDec.getBlock());

        } catch (Exception ex){
            ex.printStackTrace();
        }

    }
    
    private void parseBody(BlockStmt block){
        // System.out.println(block);
        if (block == null || block.getChildrenNodes().size() == 0){
        	this.isEmpty = true;
        	return;
        }
        this.lineCount = block.getChildrenNodes().size();
        for (Node childNode : block.getChildrenNodes()){
            parseNode(childNode);
        }
    }

    private void parseNode(Node node) {
    	// Capture all types of Expressions and Statements
    	if(node instanceof Expression){
    		this.expressions.add(node.getClass().getSimpleName());
    	} else if(node instanceof Statement){
    		this.statements.add(node.getClass().getSimpleName());
    	} 
    	
    	if(node instanceof ClassOrInterfaceDeclaration){
    		this.concepts.add("InnerClass");
    	}
    	else if(node instanceof MethodDeclaration){
    		this.concepts.add("InnerMethod");
    	}
    	
    	if (node instanceof VariableDeclaratorId){
    		incrementDictCount(this.variables, ((VariableDeclaratorId) node).getName());
    	} else if (node instanceof MethodCallExpr) {
            this.parseMethodCall((MethodCallExpr) node);
        } else if (node instanceof Expression) {
            this.parseExpr((Expression) node);
        } else if(node instanceof CatchClause){
        	CatchClause catchClause = (CatchClause) node;
        	this.exceptions.add(catchClause.getParam().getType().toString());
        }
    	
        for(Node childNode : node.getChildrenNodes()){
            parseNode(childNode);
        }
        
    }

    private static void incrementDictCount(HashMap<String, Integer> hashMap, String key){
        if (key.length() < 2){
        	// Anything of length 1 is considered trivial.
        	// Most probably variable names like x, i and numbers less than 10
        	return;
        }
    	if (hashMap.containsKey(key)){
            int count = hashMap.get(key);
            hashMap.put(key, count + 1);
        }
        else{
            hashMap.put(key, 1);
        }
    }

    private void parseExpr(Expression expr){
        if(expr instanceof NameExpr){
            // Most likely a variable name
        	String name = ((NameExpr)expr).getName();
            if (name != null){
            	// null occurs often when it involves methodCalls
            	Node parent = expr.getParentNode();
            	if(!(parent instanceof MethodCallExpr) && !(parent instanceof FieldAccessExpr)){
            		// methodCall like getPerson().getName() would also have getName and getPerson as name expressions
            		// So do fieldAccess expressions like System.out.println
            		incrementDictCount(this.variables, ((NameExpr)expr).getName());
            	}          	
            }            	
        } else if(expr instanceof VariableDeclarationExpr){        	
            int modifiers = ((VariableDeclarationExpr) expr).getModifiers();
            if (modifiers == 16){
            	this.concepts.add("FinalVariables");
            }
        	VariableDeclarationExpr varDecExpr = (VariableDeclarationExpr) expr;
            String leftType = varDecExpr.getType().toString();
            for(Node child : varDecExpr.getChildrenNodes()){
            	if (child instanceof VariableDeclarator){
            		VariableDeclarator varDeclerator = (VariableDeclarator) child;
            		if (varDeclerator.getInit() instanceof ObjectCreationExpr){
            			ObjectCreationExpr creationExpr = (ObjectCreationExpr) varDeclerator.getInit();
                		if(!creationExpr.getType().toString().equals(leftType)){
                			// Left Type is not equal to right, Polymorphism in action
                			this.concepts.add("PolyMorphism");
                			incrementDictCount(this.types, creationExpr.getType().getName());
                		}
            		} else if (varDeclerator.getInit() instanceof CastExpr){
            			this.concepts.add("Casting");
            		}
            	}
            }
            incrementDictCount(this.types, varDecExpr.getType().toString());
        } else if(expr instanceof LiteralExpr){
            // These are primarily integer and string constants
            String stringExpr = ((LiteralExpr) expr).toString().trim();
            if (!stringExpr.startsWith("//")){
            	incrementDictCount(this.constants, stringExpr);
        	}            
        }
    }

    private void parseMethodCall(MethodCallExpr methodCall){
        Expression scope = methodCall.getScope();
    	if (scope != null){
        	// Sometimes the scope could be System.out but sometimes it could be a variablename
        	// We try to fetch class names
        	if(scope instanceof FieldAccessExpr){
        		// System.out case
        		incrementDictCount(this.methodCalls, methodCall.getScope() + "." + methodCall.getName());
        	}
        	else if (scope instanceof MethodCallExpr){
        		// Called via chain of calls like getSomething().somethingElse()
        		// when we are currently looking at somethingElse
        		incrementDictCount(this.methodCalls, methodCall.getName());
        	}
        	else if (scope instanceof NameExpr){
        		// This is called via an instance variable
        		// We assign add the instance variable to the variable dict
        		incrementDictCount(this.variables, ((NameExpr) scope).getName());
        		// It would be nice if we got the full type here, like what is the class of the scope
        		incrementDictCount(this.methodCalls, methodCall.getName());
        	}
        	
        }
        else{
        	incrementDictCount(this.methodCalls, methodCall.getName());
        }        
        
        if(this.name.equals(methodCall.getName())){
            if(scope == null){
            	this.concepts.add("Recursion");
            }
            else{
            	if(scope instanceof NameExpr){
            		if(scope.toString().equals(this.className)){
            			this.concepts.add("Recursion");
            		}            		
            	}
            }
        }
    }
    
    /**
     * @return JSON representation of Enhanced - AST
     */
    public String toJSON(){
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }

    public String printAndExtractText(DocumentableNode n){
        String content = n.toString();
    	if (n.getJavaDoc() != null){
        	// JavaDoc is double counted, so ensure
    		String javaDocString = n.getJavaDoc().toString();
    		content = content.substring(content.indexOf(javaDocString) + javaDocString.length());
        }
    	
    	String[] lines = content.split("\n");
        String extracted = "";
        for(String line : lines){
            System.out.println("# " + line);
            extracted += line + "\n";
        }

        return extracted;
    }
    
    public String extractContainedComments(Node node){
    	String comments = "";
    	
    	for(Comment comment : node.getAllContainedComments()){
    		comments += cleanDocumentation(comment.getContent()) + " ";
    	}
    	
    	if(comments.isEmpty()){
    		return null;
    	}
    	
    	return comments;
    }
}
