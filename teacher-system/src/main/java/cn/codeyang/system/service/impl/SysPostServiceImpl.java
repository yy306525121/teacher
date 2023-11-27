package cn.codeyang.system.service.impl;

import cn.codeyang.common.constant.UserConstants;
import cn.codeyang.common.exception.ServiceException;
import cn.codeyang.common.utils.StringUtils;
import cn.codeyang.system.domain.SysPost;
import cn.codeyang.system.mapper.SysPostMapper;
import cn.codeyang.system.mapper.SysUserPostMapper;
import cn.codeyang.system.service.ISysPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 岗位信息 服务层处理
 *
 * @author ruoyi
 */
@Service
@RequiredArgsConstructor
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService {

    private final SysUserPostMapper userPostMapper;

    /**
     * 查询岗位信息集合
     *
     * @param post 岗位信息
     *
     * @return 岗位信息集合
     */
    @Override
    public List<SysPost> selectPostList(SysPost post) {
        return baseMapper.selectPostList(post);
    }

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    @Override
    public List<SysPost> selectPostAll() {
        return baseMapper.selectPostAll();
    }

    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     *
     * @return 角色对象信息
     */
    @Override
    public SysPost selectPostById(Long postId) {
        return baseMapper.selectPostById(postId);
    }

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     *
     * @return 选中岗位ID列表
     */
    @Override
    public List<Long> selectPostListByUserId(Long userId) {
        return baseMapper.selectPostListByUserId(userId);
    }

    /**
     * 校验岗位名称是否唯一
     *
     * @param post 岗位信息
     *
     * @return 结果
     */
    @Override
    public boolean checkPostNameUnique(SysPost post) {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPost info = baseMapper.checkPostNameUnique(post.getPostName());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验岗位编码是否唯一
     *
     * @param post 岗位信息
     *
     * @return 结果
     */
    @Override
    public boolean checkPostCodeUnique(SysPost post) {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPost info = baseMapper.checkPostCodeUnique(post.getPostCode());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param postId 岗位ID
     *
     * @return 结果
     */
    @Override
    public int countUserPostById(Long postId) {
        return userPostMapper.countUserPostById(postId);
    }

    /**
     * 删除岗位信息
     *
     * @param postId 岗位ID
     *
     * @return 结果
     */
    @Override
    public int deletePostById(Long postId) {
        return baseMapper.deletePostById(postId);
    }

    /**
     * 批量删除岗位信息
     *
     * @param postIds 需要删除的岗位ID
     *
     * @return 结果
     */
    @Override
    public int deletePostByIds(Long[] postIds) {
        for (Long postId : postIds) {
            SysPost post = selectPostById(postId);
            if (countUserPostById(postId) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", post.getPostName()));
            }
        }
        return baseMapper.deletePostByIds(postIds);
    }

    /**
     * 新增保存岗位信息
     *
     * @param post 岗位信息
     *
     * @return 结果
     */
    @Override
    public int insertPost(SysPost post) {
        return baseMapper.insertPost(post);
    }

    /**
     * 修改保存岗位信息
     *
     * @param post 岗位信息
     *
     * @return 结果
     */
    @Override
    public int updatePost(SysPost post) {
        return baseMapper.updatePost(post);
    }
}
