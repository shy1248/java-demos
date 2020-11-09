#!/usr/bin/env python
# -*- coding=UTF-8 -*-
'''
@ Since: 2020/3/20 13:18
@ Author: shy
@ Email: yushuibo@ebupt.com / hengchen2005@gmail.com
@ Version: v1.0
@ Description: a demo application for python thrift client.
'''

from __future__ import print_function

from student_gen import StudentService
from student_gen import ttypes

from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TCompactProtocol


if __name__ == '__main__':
    socket = TSocket.TSocket("localhost", 9999)
    socket.setTimeout(600)
    transport = TTransport.TFramedTransport(socket)
    protocol = TCompactProtocol.TCompactProtocol(transport)
    client = StudentService.Client(protocol)

    transport.open()

    jerry = client.getStudentByName('Jerry')
    print(jerry)

    tom = ttypes.Student()
    tom.id = 3
    tom.name = 'Tom'
    tom.age = 20
    tom.gender = ttypes.Sex.MALE
    client.save(tom)


