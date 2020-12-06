# Network/VPC resources: This will create 1 VPC with 4 Subnets
//resource "aws_vpc" "tucklets_vpc" {
//  cidr_block           = var.cidr_block
//  enable_dns_support   = true
//  enable_dns_hostnames = true
//}

//data "aws_subnet_ids" "all_subnets" {
//  vpc_id = aws_vpc.tucklets_vpc.id
//}

//resource "aws_subnet" "private" {
//  count = length(var.private_subnet_cidr_blocks)
//
//  vpc_id            = aws_vpc.tucklets_vpc.id
//  cidr_block        = var.private_subnet_cidr_blocks[count.index]
//  availability_zone = var.availability_zones[count.index]
//}

//resource "aws_subnet" "public" {
//  count = length(var.public_subnet_cidr_blocks)
//
//  vpc_id                  = aws_vpc.tucklets_vpc.id
//  cidr_block              = var.public_subnet_cidr_blocks[count.index]
//  availability_zone       = var.availability_zones[count.index]
//  map_public_ip_on_launch = true
//}

//resource "aws_internet_gateway" "main" {
//  vpc_id = aws_vpc.tucklets_vpc.id
//}

//
//resource "aws_subnet" "public" {
//  vpc_id     = aws_vpc.tucklets_vpc.id
//  cidr_block = "10.0.1.0/24"
//}
//
//resource "aws_subnet" "private" {
//  vpc_id     = aws_vpc.tucklets_vpc.id
//  cidr_block = "10.0.2.0/24"
//}
//
//resource "aws_route_table" "public" {
//  vpc_id = aws_vpc.tucklets_vpc.id
//}
//
//resource "aws_route_table" "private" {
//  vpc_id = aws_vpc.tucklets_vpc.id
//}
//
//resource "aws_route_table_association" "public_subnet" {
//  subnet_id      = aws_subnet.public.id
//  route_table_id = aws_route_table.public.id
//}
//
//resource "aws_route_table_association" "private_subnet" {
//  subnet_id      = aws_subnet.private.id
//  route_table_id = aws_route_table.private.id
//}
//
//resource "aws_eip" "nat" {
//  vpc = true
//}
//
//resource "aws_internet_gateway" "igw" {
//  vpc_id = aws_vpc.tucklets_vpc.id
//}
//
//resource "aws_nat_gateway" "ngw" {
//  subnet_id     = aws_subnet.public.id
//  allocation_id = aws_eip.nat.id
//
//  depends_on = [aws_internet_gateway.igw]
//}
//
//resource "aws_route" "public_igw" {
//  route_table_id         = aws_route_table.public.id
//  destination_cidr_block = "0.0.0.0/0"
//  gateway_id             = aws_internet_gateway.igw.id
//}
//
//resource "aws_route" "private_ngw" {
//  route_table_id         = aws_route_table.private.id
//  destination_cidr_block = "0.0.0.0/0"
//  nat_gateway_id         = aws_nat_gateway.ngw.id
//}

//output "vpc_id" {
//  value = aws_vpc.tucklets_vpc.id
//}
//
//output "public_subnet_id" {
//  value = aws_subnet.public.id
//}
//
//output "private_subnet_id" {
//  value = aws_subnet.private.id
//}

data "aws_vpc" "default" {
  default = true
}

data "aws_subnet_ids" "default" {
  vpc_id = data.aws_vpc.default.id
}

output "vpc_id" {
  value = data.aws_vpc.default
}