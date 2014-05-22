package com.cleanwise.service.api.process.operations;


public class    GeneratorUtility {

       public Object runXmlFileGenerator(Integer data,String className,String fileName) throws Exception {
          Class c=Class.forName(className);
          FileGenerator generator = (FileGenerator) c.newInstance();
          return generator.generate(data,fileName);

       }

}
