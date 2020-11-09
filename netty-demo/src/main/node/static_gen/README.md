# Node.js 环境配置

- 下载地址：[https://nodejs.org/dist/v12.16.1/node-v12.16.1-win-x64.zip](https://nodejs.org/dist/v12.16.1/node-v12.16.1-win-x64.zip)

- 解压到相关目录，此处为：`D:\applications\node-v12.16.1-win-x64`

- 创建全局模块安装目录，以便统一管理

    在`D:\applications\node-v12.16.1-win-x64`目录下新建如下2个目录：
    
    - `node_cache`
    
    - `node_global`

- 添加相关环境变量
 
    - 将`npm`命令添加进系统`path`
    
      ```
      path: D:\applications\node-v12.16.1-win-x64
      ```
    
    - 设置全局模块的安装路径 
    
      ```
      NODE_PATH: D:\applications\node-v12.16.1-win-x64\node_global\node_modules
      ```
      
    - 将全局模块安装路径添加进系统`path`中，以便命令行能搜索到安装的命令
    
      ```   
      path: D:\applications\node-v12.16.1-win-x64\node_global
      ```


- 设置全局路径

    ```shell script
    npm config set prefix "D:\applications\node-v12.16.1-win-x64\node_global"
    npm config set cache "D:\applications\node-v12.16.1-win-x64\node_cache"
    ```

- 自定义仓库位置，加快包下载速度

    ```shell script
    npm config set registry=http://registry.npm.taobao.org
    ```
  
- 查看配置

    ```shell script
    npm config list
    ```
  
- 查看仓库配置

    ```shell script
    npm config get registry
    ```
  
- 获取`grpc-tools`包信息
    
    ```shell script
    npm info grpc-tools
    ```

- 全局安装`grpc-tools`工具

    ```shell script
    npm install -g grpc-tools
    ```
  
- 列出全局安装的模块

    ```shell script
    npm list -global
    ```
- 生成`grpc`的node.js代码

    进入`proto`文件路径下，此处为`netty-demo\src\main\grpc`，命令如下：

    ```shell script
    cd .\netty-demo\src\main\grpc
    D:\applications\node-v12.16.1-win-x64\node_global\node_modules\grpc-tools\bin\protoc.exe \
  --js_out=import_style=commonjs,binary:../node/static_gen/gen \
  --grpc_out=../node/static_gen/gen \
  --plugin=protoc-gen
  -grpc="D:\applications\node-v12.16.1-win-x64\node_global\node_modules\grpc-tools\bin\grpc_node_plugin.exe" 
  greeting.proto
    ```
