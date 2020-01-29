<template>
  <div>
    <nav aria-label="...">
      <ul class="pagination pagination-sm">
        <!-- 上一页 -->
        <li :class="`page-item${param.hasPrevPage ? '' : ' disabled'}`">
          <a
            class="page-link"
            href="javascript:void(0)"
            @click="$emit('jump', param.currentPage - 1)"
            tabindex="-1"
          >上一页</a>
        </li>
        <!-- 首页 -->
        <template v-if="param.currentPage > 3">
          <li class="page-item">
            <a class="page-link" href="javascript:void(0)" @click="$emit('jump', 1)">1</a>
          </li>
          <li v-if="param.currentPage > 4" class="page-item disabled">
            <a class="page-link" href="javascript:void(0)">...</a>
          </li>
        </template>
        <li v-if="param.currentPage > 2" class="page-item">
          <a
            class="page-link"
            href="javascript:void(0)"
            @click="$emit('jump', param.currentPage - 2)"
          >{{ param.currentPage - 2 }}</a>
        </li>
        <li v-if="param.currentPage > 1" class="page-item">
          <a
            class="page-link"
            href="javascript:void(0)"
            @click="$emit('jump', param.currentPage - 1)"
          >{{ param.currentPage - 1 }}</a>
        </li>
        <!-- 当前页 -->
        <li class="page-item">
          <a
            class="page-link active-link"
            href="javascript:void(0)"
            @click="$emit('jump', param.currentPage)"
          >{{ param.currentPage }}</a>
        </li>
        <li v-if="param.currentPage + 1 <= param.totalPage" class="page-item">
          <a
            class="page-link"
            href="javascript:void(0)"
            @click="$emit('jump', param.currentPage + 1)"
          >{{ param.currentPage + 1 }}</a>
        </li>
        <li v-if="param.currentPage + 2 <= param.totalPage" class="page-item">
          <a
            class="page-link"
            href="javascript:void(0)"
            @click="$emit('jump', param.currentPage + 2)"
          >{{ param.currentPage + 2 }}</a>
        </li>
        <!-- 尾页 -->
        <template v-if="param.currentPage + 2 < param.totalPage">
          <li v-if="param.currentPage + 3 < param.totalPage" class="page-item disabled">
            <a class="page-link" href="javascript:void(0)">...</a>
          </li>
          <li class="page-item">
            <a
              class="page-link"
              href="javascript:void(0)"
              @click="$emit('jump', param.totalPage)"
            >{{ param.totalPage }}</a>
          </li>
        </template>
        <!-- 下一页 -->
        <li :class="`page-item${param.hasNextPage ? '' : ' disabled'}`">
          <a
            class="page-link"
            href="javascript:void(0)"
            @click="$emit('jump', param.currentPage + 1)"
          >下一页</a>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script>
export default {
  name: 'Page',
  props: {
    param: {
      currentPage: Number,
      pageSize: Number,
      total: Number,
      totalPage: Number,
      hasPrevPage: Boolean,
      hasNextPage: Boolean
    }
  }
}
</script>

<style>
.active-link {
  background-color: #007bff;
  color: #fff;
  border: 1px solid #007bff;
}
</style>