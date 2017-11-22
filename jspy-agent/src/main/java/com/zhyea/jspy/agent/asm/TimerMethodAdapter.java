package com.zhyea.jspy.agent.asm;

import com.zhyea.jspy.commons.tools.TimerClerk;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

public class TimerMethodAdapter extends AdviceAdapter {

    private String owner;

    private boolean isInterface;

    private int start;

    private int end;

    private boolean isSkip = false;

    private static final String systemOwner =
            System.class.getName().replaceAll("\\.", "/");

    private static final String watcherOwner =
            TimerClerk.class.getName().replaceAll("\\.", "/");

    public TimerMethodAdapter(final MethodVisitor methodVisitor,
                              final int access,
                              final String name,
                              final String desc,
                              final String owner,
                              final boolean isInterface) {
        super(ASM6, methodVisitor, access, name, desc);
        this.owner = owner + "." + name;
        this.isInterface = isInterface;
        this.isSkip = name.equals("<init>");
    }


    @Override
    protected void onMethodEnter() {
        if (!isSkip) {
            mv.visitMethodInsn(INVOKESTATIC, systemOwner, "currentTimeMillis", "()J", isInterface);
            start = newLocal(Type.LONG_TYPE);
            mv.visitVarInsn(LSTORE, start);
        }
    }


    @Override
    protected void onMethodExit(int opcode) {
        if (!isSkip) {
            mv.visitMethodInsn(INVOKESTATIC, systemOwner, "currentTimeMillis", "()J", isInterface);
            end = newLocal(Type.LONG_TYPE);
            mv.visitVarInsn(LSTORE, end);

            mv.visitLdcInsn(owner);

            mv.visitVarInsn(LLOAD, start);
            mv.visitVarInsn(LLOAD, end);
            mv.visitInsn(LSUB);

            mv.visitMethodInsn(INVOKESTATIC, watcherOwner,"add", "(Ljava/lang/String;J)V", isInterface);
        }
    }


    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack + 4, maxLocals);
    }
}