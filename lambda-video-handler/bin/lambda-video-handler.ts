#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from 'aws-cdk-lib';
import { LambdaVideoHandlerStack } from '../lib/lambda-video-handler-stack';

const app = new cdk.App();
new LambdaVideoHandlerStack(app, 'LambdaVideoHandlerStack', {
});