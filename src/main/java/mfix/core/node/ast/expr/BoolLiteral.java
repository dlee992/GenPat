/**
 * Copyright (C) SEI, PKU, PRC. - All Rights Reserved.
 * Unauthorized copying of this file via any medium is
 * strictly prohibited Proprietary and Confidential.
 * Written by Jiajun Jiang<jiajun.jiang@pku.edu.cn>.
 */
package mfix.core.node.ast.expr;

import mfix.core.node.NodeUtils;
import mfix.core.node.ast.Node;
import mfix.core.node.ast.VarScope;
import mfix.core.node.match.metric.FVector;
import mfix.core.node.modify.Adaptee;
import mfix.core.node.modify.Update;
import mfix.core.pattern.cluster.NameMapping;
import mfix.core.pattern.cluster.VIndex;
import org.eclipse.jdt.core.dom.ASTNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: Jiajun
 * @date: 2018/9/21
 */
public class BoolLiteral extends Expr {

    private static final long serialVersionUID = 2944431726908480955L;
    private boolean _value = false;

    /**
     * BooleanLiteral:
     *      true false
     */
    public BoolLiteral(String fileName, int startLine, int endLine, ASTNode node) {
        super(fileName, startLine, endLine, node);
        _nodeType = TYPE.BLITERAL;
        _fIndex = VIndex.EXP_BOOL_LIT;
    }

    public void setValue(boolean value) {
        _value = value;
    }

    public boolean getValue() {
        return _value;
    }

    @Override
    public StringBuffer toSrcString() {
        return new StringBuffer(String.valueOf(_value));
    }

    @Override
    protected StringBuffer toFormalForm0(NameMapping nameMapping, boolean parentConsidered, Set<String> keywords) {
//        return leafFormalForm(nameMapping, parentConsidered, keywords);
        if (isChanged()) {
            keywords.add(toString());
            return toSrcString();
        } else if (isConsidered()) {
            keywords.add("boolean");
            return new StringBuffer("boolean::").append(nameMapping.getExprID(this));
        } else {
            return null;
        }
    }

    @Override
    protected void tokenize() {
        _tokens = new LinkedList<>();
        _tokens.add(String.valueOf(_value));
    }

    @Override
    public List<Node> getAllChildren() {
        return new ArrayList<>(0);
    }

    @Override
    public boolean compare(Node other) {
        if (other != null && other instanceof BoolLiteral) {
            return (_value == ((BoolLiteral) other)._value);
        }
        return false;
    }

    @Override
    public void computeFeatureVector() {
        _selfFVector = new FVector();
        _selfFVector.inc(FVector.E_BOOL);
        _completeFVector = new FVector();
        _completeFVector.inc(FVector.E_BOOL);
    }

    @Override
    public boolean postAccurateMatch(Node node) {
        if (getBindingNode() == node) return true;
        if (getBindingNode() == null && canBinding(node)) {
            setBindingNode(node);
            return true;
        }
        return false;
    }

    @Override
    public boolean genModifications() {
        if (super.genModifications()) {
            BoolLiteral literal = (BoolLiteral) getBindingNode();
            if (_value != literal.getValue()) {
                Update update = new Update(this, this, literal);
                _modifications.add(update);
            }
        }
        return false;
    }

    @Override
    public StringBuffer transfer(VarScope vars, Map<String, String> exprMap, String retType, Set<String> exceptions,
                                 Adaptee metric) {
        metric.inc();
        return toSrcString();
    }

    @Override
    public StringBuffer adaptModifications(VarScope vars, Map<String, String> exprMap, String retType,
                                           Set<String> exceptions, Adaptee metric) {
        Node node = NodeUtils.checkModification(this);
        if (node != null) {
            Update update = (Update) node.getModifications().get(0);
            return update.apply(vars, exprMap, retType, exceptions, metric);
        }
        return toSrcString();
    }
}
