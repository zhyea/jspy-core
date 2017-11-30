package com.zhyea.jspy.agent.tools;

import com.zhyea.jspy.agent.tools.common.Basket;
import com.zhyea.jspy.commons.model.ObjectNode;
import junit.framework.TestCase;

public class ObjectTreeTest extends TestCase {


    public void testDump() throws IllegalAccessException {
        ObjectTree tree = new ObjectTree(new Basket());
        ObjectNode root = tree.build();
        tree.dump(root, 0);
    }


}
