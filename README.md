# JavaBaseStudy

## java基础学习笔记

### java Collection
* HashMap并发不安全，多线程操作当执行resize方法（扩展数组大小）时，可能形成环形链表（java8中将链表改成红黑树），导致get时发生死循环，cpu100%
