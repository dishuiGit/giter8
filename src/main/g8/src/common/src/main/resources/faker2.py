#!/usr/bin/env python
# -*- coding:utf-8 -*-
import sys
import random
import json
import csv
from faker import Factory

fake = Factory().create('zh_CN')


def random_phone_number():
    """随机手机号"""
    return fake.phone_number()


def random_name():
    """随机姓名"""
    return fake.name()


def random_address():
    """随机地址"""
    return fake.address()


def random_email():
    """随机email"""
    return fake.email()


def random_ipv4():
    """随机IPV4地址"""
    return fake.ipv4()


def random_str(min_chars=0, max_chars=8):
    """长度在最大值与最小值之间的随机字符串"""
    return fake.pystr(min_chars=min_chars, max_chars=max_chars)


def factory_generate_ids(starting_id=1, increment=1):
    """ 返回一个生成器函数，调用这个函数产生生成器，从starting_id开始，步长为increment。 """
    def generate_started_ids():
        val = starting_id
        local_increment = increment
        while True:
            yield val
            val += local_increment
    return generate_started_ids


def factory_choice_generator(values):
    """ 返回一个生成器函数，调用这个函数产生生成器，从给定的list中随机取一项。 """
    def choice_generator():
        my_list = list(values)
        rand = random.Random()
        while True:
            yield random.choice(my_list)
    return choice_generator


if __name__ == '__main__':
    if len(sys.argv) == 4:
        fileType = sys.argv[1]
        file_path = sys.argv[2]
        num = int(sys.argv[3])
    else:
        print('Usage: python faker1.py 文件类型 输出路径 条数')
        sys.exit(0)

    reload(sys)
    sys.setdefaultencoding( "utf-8" )

    if fileType == 'json':
        with open(file_path, 'w') as f:
            for i in range(num):
                data = {
                    'id' : fake.random_int(min=0, max=9999),
                    'name' : random_name(),
                    'phone_number' : random_phone_number(),
                    'address' : random_address(),
                    'email' : random_email(),
                    'ipv4' : random_ipv4(),
                    'str' : random_str(min_chars=6, max_chars=8)
                }
                json_str = json.dumps(data,ensure_ascii=False).encode('utf-8')
                # print json_str
                f.write(json_str)
                f.write('\n')
    elif fileType == 'csv':
        with open(file_path, 'wb') as f:
            writer = csv.writer(f)
            # writer.writerow(['id','name'])
            for i in range(num):
                data = [
                    fake.random_int(min=0, max=9999),random_name()
                ]
                writer.writerow(data)
