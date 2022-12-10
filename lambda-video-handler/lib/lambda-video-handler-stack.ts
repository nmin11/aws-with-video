import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import { IFunction } from "aws-cdk-lib/aws-lambda";
import { SpringCloudFunctionConstruct } from './spring-cloud-function-construct';

export class LambdaVideoHandlerStack extends cdk.Stack {
  readonly videoHandler: IFunction;

  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const videoHandlerFunc = new SpringCloudFunctionConstruct(this, 'video-handler', {
      functionDefinition: 'videoHandler',
      jarFilePath: 'lambda/build/libs/lambda-0.0.1-SNAPSHOT-all.jar',
      description: 'Video 썸네일 추출 람다'
    });
    this.videoHandler = videoHandlerFunc.function;
  }
}
