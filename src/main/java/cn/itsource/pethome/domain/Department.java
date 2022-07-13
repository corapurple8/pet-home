package cn.itsource.pethome.domain;
import lombok.*;

@Data//包括getset tostring hashcode和equals
@NoArgsConstructor//无参构造
@AllArgsConstructor//有参构造
public class Department {
    /**主键*/
    private Long id;
    /**部门编号*/
    private String sn;
    /**部门名称*/
    private String name;
    /**暂时不用*/
   // private String dirPath;
    /**部门状态 0 正常 ，-1 停用*/
    private int state;
    /**经理编号*/
    private Long manager_id;
    /**上级id*/
    private Long parent_id;

    /*部门经理 和员工关联*/
    private String manager;
    private Department parent;
    /*上级部门
    private Department parent;
    private List<Department> children = new ArrayList<>();
*/
}
