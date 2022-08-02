<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download"/>
          <a :href="OSS_PATH + '/excel/%E8%AF%BE%E7%A8%8B%E5%88%86%E7%B1%BB%E5%88%97%E8%A1%A8%E6%A8%A1%E6%9D%BF.xlsx'">点击下载模版</a>
        </el-tag>

      </el-form-item>

      <el-form-item label="选择Excel">
        <el-upload
          ref="upload"
          :action="BASE_API+'/admin/edu/subject/addSubject'"
          :auto-upload="false"
          :disabled="importBtnDisabled"
          :limit="1"
          :on-error="fileUploadError"
          :on-success="fileUploadSuccess"
          accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
          name="file"
        >
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button
            :loading="loading"
            size="small"
            style="margin-left: 10px;"
            type="success"
            @click="submitUpload"
          >{{ fileUploadBtnText }}
          </el-button>

          <el-button
            type="primary"
            size="mini"
            icon="el-icon-upload2"
            @click="exportData"
          >导出Excel
          </el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'Import',
  data() {
    return {
      BASE_API: process.env.VUE_APP_BASE_API, // 接口API地址
      OSS_PATH: process.env.VUE_APP_OSS_PATH, // 阿里云OSS地址
      fileUploadBtnText: '上传到服务器', // 按钮文字
      importBtnDisabled: false, // 按钮是否禁用,
      loading: false
    }
  },
  methods: {
    submitUpload() {
      this.fileUploadBtnText = '正在上传'
      this.importBtnDisabled = true
      this.loading = true
      this.$refs.upload.submit()
    },
    fileUploadSuccess(response) {
      if (response.success === true) {
        this.fileUploadBtnText = '导入成功'
        this.loading = false
        this.$message.success(response.message)
        this.$router.push("/edu/subject")
      }
    },

    fileUploadError(response) {
      this.fileUploadBtnText = '导入失败'
      this.loading = false
      this.$message.error('导入失败')
    },
    // excel导出
    exportData() {
      window.location.href = this.BASE_API + '/admin/edu/subject/exportSubject'
    }
  }
}
</script>

<style scoped>

</style>
