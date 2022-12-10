import { Construct } from 'constructs';
import { Duration } from 'aws-cdk-lib';
import { Code, Function, IFunction, Runtime } from 'aws-cdk-lib/aws-lambda';

interface SpringCloudFunctionConstructProp {
  functionDefinition: string;
  memorySize?: number;
  timeout?: Duration;
  description?: string;
  jarFilePath: string;
  environment?: Record<string, string>;
}

class SpringCloudFunctionConstruct extends Construct {
  readonly function: IFunction;

  constructor(
    scope: Construct,
    id: string,
    prop: SpringCloudFunctionConstructProp
  ) {
    super(scope, id);

    this.function = new Function(this, `${id}-func`, {
      runtime: Runtime.JAVA_11,
      code: Code.fromAsset(prop.jarFilePath),
      functionName: `${id}-function`,
      description: prop.description,
      handler: 'org.springframework.cloud.function.adapter.aws.FunctionInvoker::handlerRequest',
      memorySize: prop.memorySize || 1024,
      timeout: prop.timeout || Duration.minutes(2),
      environment: {
        ...(prop.environment || {}),
        SPRING_CLOUD_FUNCTION_DEFINITION: prop.functionDefinition,
      },
    });
  }
}

export { SpringCloudFunctionConstruct };
