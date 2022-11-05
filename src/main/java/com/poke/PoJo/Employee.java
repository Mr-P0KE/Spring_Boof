package com.poke.PoJo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * Ա����Ϣ
 * @TableName employee
 */
@TableName(value ="employee")
@Data
public class Employee implements Serializable {
    /**
     * ����
     */
    @TableId
    private Long id;

    /**
     * ����
     */
    private String name;

    /**
     * �û���
     */
    private String username;

    /**
     * ����
     */
    private String password;

    /**
     * �ֻ���
     */
    private String phone;

    /**
     * �Ա�
     */
    private String sex;

    /**
     * ���֤��
     */
    private String idNumber;

    /**
     * ״̬ 0:���ã�1:����
     */
    private Integer status;

    /**
     * ����ʱ��
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * ����ʱ��
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * ������
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * �޸���
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}