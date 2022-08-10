

package com.aison.util;

import com.aison.dto.MenuTree;
import com.aison.dto.TreeNode;
import com.aison.entity.TMenu;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fixassets
 * @date 2020-02-09
 */
@Component
public class MenuTreeUtil {

	/**
	 * 两层循环实现建树
	 * @param treeNodes 传入的树节点列表
	 * @return
	 */
	public static <T extends TreeNode> List<T> build(List<T> treeNodes, Object root) {

		List<T> trees = new ArrayList<>();

		for (T treeNode : treeNodes) {

			if (root.equals(treeNode.getParentId())) {
				trees.add(treeNode);
			}

			for (T it : treeNodes) {
				if (it.getParentId().equals(treeNode.getId())) {
					if (treeNode.getChildren() == null) {

						treeNode.setChildren(new ArrayList<>());

					}
					treeNode.add(it);
				}
			}
		}
		return trees;
	}

	/**
	 * 使用递归方法建树
	 * @param treeNodes
	 * @return
	 */
	public <T extends TreeNode> List<T> buildByRecursive(List<T> treeNodes, Object root) {
		List<T> trees = new ArrayList<T>();
		for (T treeNode : treeNodes) {
			if (root.equals(treeNode.getParentId())) {
				trees.add(findChildren(treeNode, treeNodes));
			}
		}
		return trees;
	}

	/**
	 * 递归查找子节点
	 * @param treeNodes
	 * @return
	 */
	public <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
		for (T it : treeNodes) {
			if (treeNode.getId() == it.getParentId()) {
				if (treeNode.getChildren() == null) {
					treeNode.setChildren(new ArrayList<>());
				}
				treeNode.add(findChildren(it, treeNodes));
			}
		}
		return treeNode;
	}

	/**
	 * 通过sysMenu创建树形节点
	 * @param menus
	 * @param root
	 * @return
	 */
	public static List<MenuTree> buildTree(List<TMenu> menus, Long root) {
		List<MenuTree> trees = new ArrayList<>();
		MenuTree node;
		for (TMenu menu : menus) {
			node = new MenuTree();
			node.setId(menu.getMenuId());
			node.setParentId(menu.getParentId());
			node.setName(menu.getMenuName());
			node.setPath(menu.getPath());
			node.setIcon(menu.getIcon());
			node.setType(menu.getType());
			node.setSort(menu.getSort());
			node.setHasChildren(false);
			node.setOpen(menu.getOpen());
			node.setDisabled(menu.getDisabled());
			node.setSelected(menu.getSelected());
			node.setLevel(menu.getLevel());
			trees.add(node);
		}
		return MenuTreeUtil.build(trees, root);
	}

}
