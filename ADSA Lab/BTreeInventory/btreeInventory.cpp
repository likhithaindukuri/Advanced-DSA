#include <iostream>
using namespace std;
struct Node
{
    int key[3], n;
    Node *child[4];
    bool leaf;
};
Node *createNode(bool leaf)
{
    Node *p = new Node;
    p->n = 0;
    p->leaf = leaf;
    for (int i = 0; i < 4; i++)
        p->child[i] = NULL;
    return p;
}
bool search(Node *p, int k)
{
    if (p == NULL)
        return false;
    int i = 0;
    while (i < p->n && k > p->key[i])
        i++;
    if (i < p->n && p->key[i] == k)
        return true;
    if (p->leaf)
        return false;
    return search(p->child[i], k);
}
void split(Node *p, int i)
{
    Node *y = p->child[i];
    Node *z = createNode(y->leaf);
    z->n = 1;
    z->key[0] = y->key[2];
    if (!y->leaf)
    {
        z->child[0] = y->child[2];
        z->child[1] = y->child[3];
    }
    int mid = y->key[1];
    y->n = 1;
    for (int j = p->n; j >= i + 1; j--)
        p->child[j + 1] = p->child[j];
    p->child[i + 1] = z;
    for (int j = p->n - 1; j >= i; j--)
        p->key[j + 1] = p->key[j];
    p->key[i] = mid;
    p->n++;
}
void insertNonFull(Node *p, int k)
{
    int i = p->n - 1;
    if (p->leaf)
    {
        while (i >= 0 && k < p->key[i])
        {
            p->key[i + 1] = p->key[i];
            i--;
        }
        p->key[i + 1] = k;
        p->n++;
    }
    else
    {
        while (i >= 0 && k < p->key[i])
            i--;
        i++;
        if (p->child[i]->n == 3)
        {
            split(p, i);
            if (k > p->key[i])
                i++;
        }
        insertNonFull(p->child[i], k);
    }
}
void insert(Node *&root, int k)
{
    if (search(root, k))
        return;
    if (root == NULL)
    {
        root = createNode(true);
        root->key[0] = k;
        root->n = 1;
        return;
    }
    if (root->n == 3)
    {
        Node *p = createNode(false);
        p->child[0] = root;
        split(p, 0);
        int i = (k > p->key[0]) ? 1 : 0;
        insertNonFull(p->child[i], k);
        root = p;
    }
    else
    {
        insertNonFull(root, k);
    }
}
int pred(Node *p)
{
    while (!p->leaf)
        p = p->child[p->n];
    return p->key[p->n - 1];
}
int succ(Node *p)
{
    while (!p->leaf)
        p = p->child[0];
    return p->key[0];
}
void borrowPrev(Node *p, int i)
{
    Node *c = p->child[i];
    Node *s = p->child[i - 1];
    for (int j = c->n - 1; j >= 0; j--)
        c->key[j + 1] = c->key[j];
    if (!c->leaf)
    {
        for (int j = c->n; j >= 0; j--)
            c->child[j + 1] = c->child[j];
        c->child[0] = s->child[s->n];
    }
    c->key[0] = p->key[i - 1];
    p->key[i - 1] = s->key[s->n - 1];
    c->n++;
    s->n--;
}
void borrowNext(Node *p, int i)
{
    Node *c = p->child[i];
    Node *s = p->child[i + 1];
    c->key[c->n] = p->key[i];
    if (!c->leaf)
        c->child[c->n + 1] = s->child[0];
    p->key[i] = s->key[0];
    for (int j = 1; j < s->n; j++)
        s->key[j - 1] = s->key[j];
    if (!s->leaf)
    {
        for (int j = 1; j <= s->n; j++)
            s->child[j - 1] = s->child[j];
    }
    c->n++;
    s->n--;
}
void merge(Node *p, int i)
{
    Node *c = p->child[i];
    Node *s = p->child[i + 1];
    c->key[1] = p->key[i];
    for (int j = 0; j < s->n; j++)
        c->key[j + 2] = s->key[j];
    if (!c->leaf)
    {
        for (int j = 0; j <= s->n; j++)
            c->child[j + 2] = s->child[j];
    }
    c->n += s->n + 1;
    for (int j = i + 1; j < p->n; j++)
        p->key[j - 1] = p->key[j];
    for (int j = i + 2; j <= p->n; j++)
        p->child[j - 1] = p->child[j];
    p->n--;
    delete s;
}
void fill(Node *p, int i)
{
    if (i > 0 && p->child[i - 1]->n >= 2)
        borrowPrev(p, i);
    else if (i < p->n && p->child[i + 1]->n >= 2)
        borrowNext(p, i);
    else if (i < p->n)
        merge(p, i);
    else
        merge(p, i - 1);
}
void deleteKey(Node *p, int k)
{
    int i = 0;
    while (i < p->n && p->key[i] < k)
        i++;
    if (i < p->n && p->key[i] == k)
    {
        if (p->leaf)
        {
            for (int j = i + 1; j < p->n; j++)
                p->key[j - 1] = p->key[j];
            p->n--;
        }
        else if (p->child[i]->n >= 2)
        {
            int x = pred(p->child[i]);
            p->key[i] = x;
            deleteKey(p->child[i], x);
        }
        else if (p->child[i + 1]->n >= 2)
        {
            int x = succ(p->child[i + 1]);
            p->key[i] = x;
            deleteKey(p->child[i + 1], x);
        }
        else
        {
            merge(p, i);
            deleteKey(p->child[i], k);
        }
    }
    else
    {
        if (p->leaf)
            return;
        bool last = (i == p->n);
        if (p->child[i]->n == 1)
            fill(p, i);
        if (last && i > p->n)
            deleteKey(p->child[i - 1], k);
        else
            deleteKey(p->child[i], k);
    }
}
void remove(Node *&root, int k)
{
    if (!search(root, k))
        return;
    deleteKey(root, k);
    if (root->n == 0)
    {
        Node *temp = root;
        if (root->leaf)
            root = NULL;
        else
            root = root->child[0];
        delete temp;
    }
}
void display(Node *root)
{
    if (root == NULL)
        return;
    cout << "[";
    for (int i = 0; i < root->n; i++)
        cout << root->key[i] << " ";
    cout << "] ";
    if (!root->leaf)
    {
        for (int i = 0; i <= root->n; i++)
            display(root->child[i]);
    }
}
int main()
{
    Node *root = NULL;
    int a[] = {120, 80, 200, 50, 95, 150, 230, 30, 60, 90, 110, 170, 250, 140};
    for (int x : a)
        insert(root, x);
    cout << "After insertion: ";
    display(root);
    remove(root, 50);
    remove(root, 200);
    remove(root, 120);
    cout << "\nAfter deletion: ";
    display(root);
    cout << "\nSearch 110: ";
    cout << (search(root, 110) ? "Found" : "Not Found");
    return 0;
}