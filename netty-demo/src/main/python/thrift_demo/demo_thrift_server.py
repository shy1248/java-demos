#!/usr/bin/env python
# -*- coding=UTF-8 -*-
'''
@ Since: 2020/3/20 14:24
@ Author: shy
@ Email: yushuibo@ebupt.com / hengchen2005@gmail.com
@ Version: v1.0
@ Description: a demo application for python thrift server.
'''

from __future__ import print_function

from thrift.protocol import TCompactProtocol
from thrift.server import TServer
from thrift.transport import TTransport
from thrift.transport import TSocket

from student_gen import StudentService
from student_gen import ttypes


class StudentServiceImpl(StudentService.Iface):

    def getStudentByName(self, name):
        print('Called get API with prama: {}'.format(name))
        stu = ttypes.Student()
        stu.id = 100
        stu.name = "sharly"
        stu.age = 24
        stu.gender = ttypes.Sex.FEMALE
        print(stu)
        return stu

    def save(self, student):
        print('Called save API with prama: {}'.format(student))
        print('{} is saved!'.format(student))


if __name__ == '__main__':
    socket = TSocket.TServerSocket('0.0.0.0', 9999)
    transportFactory = TTransport.TFramedTransportFactory()
    protocolFactory = TCompactProtocol.TCompactProtocolFactory()
    processor = StudentService.Processor(StudentServiceImpl())

    server = TServer.TThreadPoolServer(processor, socket, transportFactory,
                                            protocolFactory, threads=2)

    print('Server startup to listen 9999 ...')
    server.serve()
