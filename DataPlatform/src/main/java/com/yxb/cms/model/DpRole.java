package com.yxb.cms.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
/**
*
*  @author rrd
*/
public class DpRole implements Serializable {

    private static final long serialVersionUID = 1528366437713L;


    /**
    * 主键
    * 
    * isNullAble:0
    */
    private String roleId;

    /**
    * 
    * isNullAble:1
    */
    private String icon;

    /**
    * 
    * isNullAble:0
    */
    private String key;

    /**
    * 
    * isNullAble:1
    */
    private String name;

    private DpRole[] menu;

    public void setMenu(DpRole[] menu){ this.menu = menu;}

    public DpRole[] getMenu(){ return this.menu; }

    public void setRoleId(String roleId){
        this.roleId = roleId;
    }

    public String getRoleId(){
        return this.roleId;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }

    public String getIcon(){
        return this.icon;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return this.key;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    @Override
    public String toString() {
        return "DpRole{" +
                "roleId='" + roleId + '\'' +
                "icon='" + icon + '\'' +
                "key='" + key + '\'' +
                "name='" + name + '\'' +
            '}';
    }

    public static QueryBuilder QueryBuild(){
        return new QueryBuilder();
    }

    public static class QueryBuilder extends DpRole{
        /**
        * 需要返回的列
        */
        private Map<String,Object> fetchFields;

        public Map<String,Object> getFetchFields(){
            return this.fetchFields;
        }

        private List<String> roleIdList;


        private List<String> fuzzyRoleId;

        public List<String> getFuzzyRoleId(){
            return this.fuzzyRoleId;
        }

        private List<String> rightFuzzyRoleId;

        public List<String> getRightFuzzyRoleId(){
            return this.rightFuzzyRoleId;
        }
        private List<String> iconList;


        private List<String> fuzzyIcon;

        public List<String> getFuzzyIcon(){
            return this.fuzzyIcon;
        }

        private List<String> rightFuzzyIcon;

        public List<String> getRightFuzzyIcon(){
            return this.rightFuzzyIcon;
        }
        private List<String> keyList;


        private List<String> fuzzyKey;

        public List<String> getFuzzyKey(){
            return this.fuzzyKey;
        }

        private List<String> rightFuzzyKey;

        public List<String> getRightFuzzyKey(){
            return this.rightFuzzyKey;
        }
        private List<String> nameList;


        private List<String> fuzzyName;

        public List<String> getFuzzyName(){
            return this.fuzzyName;
        }

        private List<String> rightFuzzyName;

        public List<String> getRightFuzzyName(){
            return this.rightFuzzyName;
        }
        private QueryBuilder (){
            this.fetchFields = new HashMap<>();
        }


        public QueryBuilder fuzzyRoleId (List<String> fuzzyRoleId){
            this.fuzzyRoleId = fuzzyRoleId;
            return this;
        }

        public QueryBuilder fuzzyRoleId (String ... fuzzyRoleId){
            if (fuzzyRoleId != null){
                List<String> list = new ArrayList<>();
                for (String item : fuzzyRoleId){
                    if (item != null){
                        list.add(item);
                    }
                }
                this.fuzzyRoleId = list;
            }
            return this;
        }

        public QueryBuilder rightFuzzyRoleId (List<String> rightFuzzyRoleId){
            this.rightFuzzyRoleId = rightFuzzyRoleId;
            return this;
        }

        public QueryBuilder rightFuzzyRoleId (String ... rightFuzzyRoleId){
            if (rightFuzzyRoleId != null){
                List<String> list = new ArrayList<>();
                for (String item : rightFuzzyRoleId){
                    if (item != null){
                        list.add(item);
                    }
                }
                this.rightFuzzyRoleId = list;
            }
            return this;
        }

        public QueryBuilder roleId(String roleId){
            setRoleId(roleId);
            return this;
        }

        public QueryBuilder roleIdList(String ... roleId){
            if (roleId != null){
                List<String> list = new ArrayList<>();
                for (String item : roleId){
                    if (item != null){
                        list.add(item);
                    }
                }
                this.roleIdList = list;
            }

            return this;
        }

        public QueryBuilder roleIdList(List<String> roleId){
            this.roleIdList = roleId;
            return this;
        }

        public QueryBuilder fetchRoleId(){
            setFetchFields("fetchFields","roleId");
            return this;
        }

        public QueryBuilder excludeRoleId(){
            setFetchFields("excludeFields","roleId");
            return this;
        }



        public QueryBuilder fuzzyIcon (List<String> fuzzyIcon){
            this.fuzzyIcon = fuzzyIcon;
            return this;
        }

        public QueryBuilder fuzzyIcon (String ... fuzzyIcon){
            if (fuzzyIcon != null){
                List<String> list = new ArrayList<>();
                for (String item : fuzzyIcon){
                    if (item != null){
                        list.add(item);
                    }
                }
                this.fuzzyIcon = list;
            }
            return this;
        }

        public QueryBuilder rightFuzzyIcon (List<String> rightFuzzyIcon){
            this.rightFuzzyIcon = rightFuzzyIcon;
            return this;
        }

        public QueryBuilder rightFuzzyIcon (String ... rightFuzzyIcon){
            if (rightFuzzyIcon != null){
                List<String> list = new ArrayList<>();
                for (String item : rightFuzzyIcon){
                    if (item != null){
                        list.add(item);
                    }
                }
                this.rightFuzzyIcon = list;
            }
            return this;
        }

        public QueryBuilder icon(String icon){
            setIcon(icon);
            return this;
        }

        public QueryBuilder iconList(String ... icon){
            if (icon != null){
                List<String> list = new ArrayList<>();
                for (String item : icon){
                    if (item != null){
                        list.add(item);
                    }
                }
                this.iconList = list;
            }

            return this;
        }

        public QueryBuilder iconList(List<String> icon){
            this.iconList = icon;
            return this;
        }

        public QueryBuilder fetchIcon(){
            setFetchFields("fetchFields","icon");
            return this;
        }

        public QueryBuilder excludeIcon(){
            setFetchFields("excludeFields","icon");
            return this;
        }



        public QueryBuilder fuzzyKey (List<String> fuzzyKey){
            this.fuzzyKey = fuzzyKey;
            return this;
        }

        public QueryBuilder fuzzyKey (String ... fuzzyKey){
            if (fuzzyKey != null){
                List<String> list = new ArrayList<>();
                for (String item : fuzzyKey){
                    if (item != null){
                        list.add(item);
                    }
                }
                this.fuzzyKey = list;
            }
            return this;
        }

        public QueryBuilder rightFuzzyKey (List<String> rightFuzzyKey){
            this.rightFuzzyKey = rightFuzzyKey;
            return this;
        }

        public QueryBuilder rightFuzzyKey (String ... rightFuzzyKey){
            if (rightFuzzyKey != null){
                List<String> list = new ArrayList<>();
                for (String item : rightFuzzyKey){
                    if (item != null){
                        list.add(item);
                    }
                }
                this.rightFuzzyKey = list;
            }
            return this;
        }

        public QueryBuilder key(String key){
            setKey(key);
            return this;
        }

        public QueryBuilder keyList(String ... key){
            if (key != null){
                List<String> list = new ArrayList<>();
                for (String item : key){
                    if (item != null){
                        list.add(item);
                    }
                }
                this.keyList = list;
            }

            return this;
        }

        public QueryBuilder keyList(List<String> key){
            this.keyList = key;
            return this;
        }

        public QueryBuilder fetchKey(){
            setFetchFields("fetchFields","key");
            return this;
        }

        public QueryBuilder excludeKey(){
            setFetchFields("excludeFields","key");
            return this;
        }



        public QueryBuilder fuzzyName (List<String> fuzzyName){
            this.fuzzyName = fuzzyName;
            return this;
        }

        public QueryBuilder fuzzyName (String ... fuzzyName){
            if (fuzzyName != null){
                List<String> list = new ArrayList<>();
                for (String item : fuzzyName){
                    if (item != null){
                        list.add(item);
                    }
                }
                this.fuzzyName = list;
            }
            return this;
        }

        public QueryBuilder rightFuzzyName (List<String> rightFuzzyName){
            this.rightFuzzyName = rightFuzzyName;
            return this;
        }

        public QueryBuilder rightFuzzyName (String ... rightFuzzyName){
            if (rightFuzzyName != null){
                List<String> list = new ArrayList<>();
                for (String item : rightFuzzyName){
                    if (item != null){
                        list.add(item);
                    }
                }
                this.rightFuzzyName = list;
            }
            return this;
        }

        public QueryBuilder name(String name){
            setName(name);
            return this;
        }

        public QueryBuilder nameList(String ... name){
            if (name != null){
                List<String> list = new ArrayList<>();
                for (String item : name){
                    if (item != null){
                        list.add(item);
                    }
                }
                this.nameList = list;
            }

            return this;
        }

        public QueryBuilder nameList(List<String> name){
            this.nameList = name;
            return this;
        }

        public QueryBuilder fetchName(){
            setFetchFields("fetchFields","name");
            return this;
        }

        public QueryBuilder excludeName(){
            setFetchFields("excludeFields","name");
            return this;
        }

        public QueryBuilder fetchAll(){
            this.fetchFields.put("AllFields",true);
            return this;
        }

        public QueryBuilder addField(String ... fields){
            List<String> list = new ArrayList<>();
            if (fields != null){
                for (String field : fields){
                    list.add(field);
                }
            }
            this.fetchFields.put("otherFields",list);
            return this;
        }
        @SuppressWarnings("unchecked")
        private void setFetchFields(String key,String val){
            Map<String,Boolean> fields= (Map<String, Boolean>) this.fetchFields.get(key);
            if (fields == null){
                fields = new HashMap<>();
            }
            fields.put(val,true);
            this.fetchFields.put(key,fields);
        }

        public DpRole build(){
            return this;
        }
    }

}
