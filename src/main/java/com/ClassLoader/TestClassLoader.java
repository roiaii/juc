package com.ClassLoader;

public class TestClassLoader {
    public static void main(String[] args) {
        //模拟实现自定义类加载器

    }
}


class UserClassLoader extends ClassLoader{
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException{
        if(name.startsWith("java") || name.startsWith("javax")){
            //如果是核心类库 交给父类加载器
            super.loadClass(name);
        }

        //其他类尝试自定义的类加载逻辑 而不是一开始就委派给父类加载器去加载
        try{
            byte[] classData = getClassData(name);
            if(classData == null){
                throw new ClassNotFoundException(name);
            }
            return defineClass(name, classData, 0, classData.length);
        }catch(Exception e){
            throw new ClassNotFoundException(name);
        }
    }

    private byte[] getClassData(String className) throws ClassNotFoundException{
        //自定义实现类加载字节码


        return null;
    }
}
