iac-role-deploy:
	aws cloudformation create-stack --stack-name ogata-cloudformation-iam-role \
	--template-body file://cloudformation/cloudformation-iam-role.yml \
	--capabilities CAPABILITY_NAMED_IAM \
	--parameters ParameterKey=GithubAccount,ParameterValue=$(GITHUB_ACCOUNT) \
	             ParameterKey=GithubRepository,ParameterValue=$(GITHUB_REPOSITORY) \
	--region ap-northeast-1

iac-role-update:
	aws cloudformation update-stack --stack-name ogata-cloudformation-iam-role \
	--template-body file://cloudformation/cloudformation-iam-role.yml \
	--capabilities CAPABILITY_NAMED_IAM \
	--parameters ParameterKey=GithubAccount,ParameterValue=$(GITHUB_ACCOUNT) \
	             ParameterKey=GithubRepository,ParameterValue=$(GITHUB_REPOSITORY) \
	--region ap-northeast-1


iac-wafacl-deploy:
	aws cloudformation create-stack --stack-name ogata-cloudformation-wafacl \
	--template-body file://cloudformation/cloudformation-wafacl.yml \
	--capabilities CAPABILITY_NAMED_IAM \
	--region us-east-1

iac-wafacl-update:
	aws cloudformation update-stack --stack-name ogata-cloudformation-wafacl \
	--template-body file://cloudformation/cloudformation-wafacl.yml \
	--capabilities CAPABILITY_NAMED_IAM \
	--region us-east-1

iac-cloudfront-deploy:
	aws cloudformation create-stack --stack-name ogata-cloudformation-cloudfront \
	--template-body file://cloudformation/cloudformation-cloudfront.yml \
	--capabilities CAPABILITY_NAMED_IAM \
	--parameters ParameterKey=WAFWebACLArn,ParameterValue=$(WAF_ACL_ARN) \
	             ParameterKey=HostZone,ParameterValue=$(HOSTED_ZONE_ID) \
	             ParameterKey=AcmArn,ParameterValue=$(ACM_CERTIFICATE_ARN_CLOUDFRONT) \
	             ParameterKey=DomainName,ParameterValue=$(DOMAIN_NAME) \
	--region ap-northeast-1

iac-cloudfront-update:
	aws cloudformation update-stack --stack-name ogata-cloudformation-cloudfront \
	--template-body file://cloudformation/cloudformation-cloudfront.yml \
	--capabilities CAPABILITY_NAMED_IAM \
	--parameters ParameterKey=WAFWebACLArn,ParameterValue=$(WAF_ACL_ARN) \
                 ParameterKey=HostZone,ParameterValue=$(HOSTED_ZONE_ID) \
                 ParameterKey=AcmArn,ParameterValue=$(ACM_CERTIFICATE_ARN_CLOUDFRONT) \
                 ParameterKey=DomainName,ParameterValue=$(DOMAIN_NAME) \
	--region ap-northeast-1