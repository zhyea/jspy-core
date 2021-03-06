package org.chobit.jspy.utils;

import org.chobit.jspy.core.exceptions.JSpyException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import static org.chobit.jspy.core.utils.Strings.isNotBlank;

public abstract class Reflections {


    /**
     * 在超类所在的包下获取超类的所有子类
     *
     * @param superClass 超类
     * @param <T>        超类类型
     * @return 超类的子类集合
     */
    public static <T> Set<Class<? extends T>> subTypeOf(Class<T> superClass) {
        return subTypeOf(superClass, superClass.getPackage().getName());
    }

    /**
     * 获取超类的所有子类
     *
     * @param superClass  超类
     * @param packageName 子类所在的包
     * @param <T>         超类类型
     * @return 超类的子类集合
     */
    public static <T> Set<Class<? extends T>> subTypeOf(Class<T> superClass, String packageName) {
        Set<Class<? extends T>> set = new HashSet<>(8);
        try {
            Set<Class<?>> classes = classesInPackage(packageName);
            for (Class clazz : classes) {
                if (superClass.isAssignableFrom(clazz)) {
                    if (!superClass.equals(clazz)) {
                        set.add(clazz);
                    }
                }
            }
        } catch (Exception e) {
            throw new JSpyException(e);
        }
        return set;
    }


    /**
     * 获取指定包下的所有类
     */
    public static Set<Class<?>> classesInPackage(String packageName) {
        Set<Class<?>> set = new HashSet<>(8);
        try {
            Enumeration<URL> urls = classLoader().getResources(packageName.replace('.', '/'));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (null == url) {
                    continue;
                }
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String packagePath = url.getPath().replaceAll("%20", "");
                    addClassFromDirectory(set, packagePath, packageName);
                } else if ("jar".equals(protocol)) {
                    addClassFromJar(set, url);
                }
            }
        } catch (Exception e) {
            throw new JSpyException(e);
        }
        return set;
    }


    /**
     * 加载类
     *
     * @param className  类名称
     * @param initialize 是否初始化类实例
     * @return 类实例
     */
    public static Class<?> loadClass(String className, boolean initialize) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className, initialize, classLoader());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return clazz;
    }


    private static void addClassFromJar(Set<Class<?>> set, URL url) throws IOException {
        URLConnection conn = url.openConnection();
        if (null == conn) {
            return;
        }
        JarURLConnection jarConn = (JarURLConnection) conn;
        JarFile jarFile = jarConn.getJarFile();
        if (null == jarFile) {
            return;
        }
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String entryName = entry.getName();
            if (entryName.endsWith(".class")) {
                String className = entryName.substring(0, entryName.lastIndexOf("."))
                        .replace('.', '/');
                set.add(loadClass(className, false));
            }
        }
    }

    private static void addClassFromDirectory(Set<Class<?>> set, String packagePath, String packageName) {
        File[] files = new File(packagePath)
                .listFiles(f -> f.isDirectory() || (f.isFile() && f.getName().endsWith(".class")));
        for (File f : files) {
            String fileName = f.getName();
            if (f.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (isNotBlank(packageName)) {
                    className = packageName + "." + className;
                }
                set.add(loadClass(className, false));
            } else if (f.isDirectory()) {
                String packagePath0 = fileName;
                if (isNotBlank(packagePath)) {
                    packagePath0 = packagePath + File.separator + f.getName();
                }
                String packageName0 = packageName + "." + fileName;
                addClassFromDirectory(set, packagePath0, packageName0);
            }
        }
    }


    /**
     * 获取当前类加载器
     */
    public static ClassLoader classLoader() {
        return Reflections.class.getClassLoader();
    }


    /**
     * 获取类的所有属性成员的名称
     */
    public static List<String> nameOfFields(Class<?> clazz) {
        return fieldsOf(clazz).stream().map(Field::getName).collect(Collectors.toList());
    }

    /**
     * 获取类的所有属性成员，含父类成员
     */
    public static List<Field> fieldsOf(Class clazz) {
        return fieldsOf(clazz, true);
    }

    /**
     * 获取类的所有属性成员
     *
     * @param clazz       目标类
     * @param recursively 是否递归获取父类成员
     * @return 类的所有属性成员
     */
    public static List<Field> fieldsOf(Class clazz, boolean recursively) {
        List<Field> fields = new LinkedList<>();
        Class tmp = clazz;

        while (null != tmp) {
            Field[] arr = tmp.getDeclaredFields();
            fields.addAll(Arrays.asList(arr));

            if (recursively) {
                tmp = tmp.getSuperclass();
            } else {
                tmp = null;
            }
        }

        return fields;
    }

    private Reflections() {
    }

}
