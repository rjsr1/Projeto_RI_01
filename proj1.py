# -*- coding: utf-8 -*-
"""
Created on Sat Oct  7 15:25:09 2017

@author: MarcusVinicius
"""
from __future__ import division, unicode_literals

 
from time import time
import timeit
import codecs
import numpy as np
from bs4 import BeautifulSoup
from sklearn.pipeline import Pipeline
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfTransformer

from sklearn.naive_bayes import  MultinomialNB
from sklearn.linear_model import SGDClassifier
from sklearn.neural_network import MLPClassifier
from sklearn.tree import DecisionTreeClassifier

from sklearn import metrics


TAM_DATASET = 200
TAM_TRAIN_SET = 140
TAM_TEST_SET = 60

#escrever info de uma pagina num arquivo de saida
#funcao de testes
def write_file(data):

    file_name = r'C:\Users\MarcusVinicius\Documents\Universidade\2017_2\Recuperacao_info\projeto1\dataset\output.txt'
    with open(file_name, 'w') as x_file:
        x_file.write(data)
        

#essa funcao recebe o nome do arquivo html, e adiciona a representacao em texto dele na lista de strings
#em que sera feito o feature selection

def parse_html(file_name, lista):
    
    f = codecs.open(file_name, 'r', 'utf-8', 'replace') #pode dar problema usar esse encoding, mas
    #a opcao replace ignora erros de caracters substituindo por ? na string, que no final das contas
    #vai ser desconsiderado do vocabulario
    
    
    
    with f as x_file:
        soup = BeautifulSoup(x_file.read())
    
    # kill all script and style elements
    for script in soup(["script", "style"]):
        
        script.decompose()
        
    # get text
    text = ' '.join(  soup.stripped_strings  )

    lista.append(text)

 
#essa funcao le todos os arquivos html para montar o dataset
#colocar as paginas num diretorio dataset, dividindo primeira metade positivas e segunda metade negativas
def ler_trainset(lista):    
    
    
    PATH_FILE = './paginas_positivas/trainset' #lendo da pasta de paginas positivas
    i = 1
    metade_dataset = int(TAM_TRAIN_SET / 2)
    
    #ler paginas positivas
    while i < (metade_dataset + 1):
        file_name = PATH_FILE + ' (' +  str(i) + ').htm'  
        parse_html(file_name, lista)
        i += 1
    
    #ler paginas negativas
    PATH_FILE = './paginas_negativas/trainset'
    i = 1
    while i < (metade_dataset + 1):
        file_name = PATH_FILE + ' (' + str(i) + ').htm' 
        parse_html(file_name, lista)
        i += 1

#ler dados de teste, metade positiva metade negativa
def ler_testset(lista):
    PATH_FILE = './teste/teste_pos/testset' #lendo da pasta de paginas positivas
    i = 1
    metade_dataset = int(TAM_TEST_SET / 2)
    
    #ler paginas positivas
    while i < (metade_dataset + 1):
        file_name = PATH_FILE + ' (' +  str(i) + ').htm'  
        parse_html(file_name, lista)
        i += 1
    
    #ler paginas negativas
    PATH_FILE = './teste/teste_neg/testset'
    i = 1
    while i < (metade_dataset + 1):
        file_name = PATH_FILE + ' (' + str(i) + ').htm' 
        parse_html(file_name, lista)
        i += 1
    

#ler dados de teste do 
#isso foi usado para testar a classificacao nas paginas coletadas pelo crawler, 
#1000 em cada um dos 10 sites do domínio
def ler_data_teste(lista):
    PATH_FILE = './Resultados_VivaLocal_BuscaLargura/' #lendo da pasta raiz onde esta o arquivo do projeto
    i = 0 #a partir dessse valor os dados sao para teste
    
    while i < 1000:
        file_name = PATH_FILE + str(i) + '.html'
        with codecs.open(file_name, 'r','utf-8', 'replace') as myfile:
            data = myfile.read()
            lista.append(data)

        i += 1



#dados de treinamento e teste dos modelos, rotulados manualmente com 10 paginas positivas
#e 10 negativas por site
lista_data = [] #tamanho 200
lista_treinamento = [] #70 positivos e 70 negativos
lista_teste = [] #30 positivos e 30 negativos
lista_teste_crawler = []
targets_teste = [1]*30 + [0]*30
targets_treinamento = [1]*70 + [0]*70




#ler arquivos html para a lista
t0  = timeit.default_timer()
ler_trainset(lista_treinamento)
ler_testset(lista_teste)
duration_read = timeit.default_timer() - t0



#formatar os dados para o scikit
vectorizer = CountVectorizer()
train_count = vectorizer.fit_transform(lista_treinamento) #componente de alto nivel para tokenizar, tratar stopwords
#de forma simplificada, constroi vocabulário; conta a ocorrencia de um termo num documento


tfidf_transformer = TfidfTransformer(smooth_idf=False) #calcular tf - idf

#usar tf idf para normalizar o efeito de palavras muito frequentes e criar features de ponto flutuante
train_tfidf = tfidf_transformer.fit_transform(train_count)




#testar classificafdor NB
#clf = MultinomialNB().fit(train_tfidf, targets_treinamento)



#usar conjunto de teste
test_count = vectorizer.transform(lista_teste) #chamar transform em vez de fit_transform assume que esses



#dados estao modelados ao conjunto de treinamento. Assumir isso como valido ja que esse conjunto
#veio do mesmo diretorio dos sites
test_tfidf = tfidf_transformer.transform(test_count)

#usando pipeline

#Bayes
'''
text_clf = Pipeline([('vect', CountVectorizer()), ('tfidf', TfidfTransformer()), ('clf', MultinomialNB())])
'''

#SVM
'''
text_clf = Pipeline([('vect', CountVectorizer()),('tfidf', TfidfTransformer()),
                     ('clf', SGDClassifier(loss='hinge', penalty='l2',
                                           alpha=1e-3, random_state=42,
                                        n_iter=5)),])
'''

#MLP
'''
text_clf = Pipeline([('vect', CountVectorizer()),('tfidf', TfidfTransformer()),
                     ('clf', 
                      MLPClassifier(solver='lbfgs', alpha=1e-5,hidden_layer_sizes=(5, 2), random_state=1)),])
'''
#Decision Tree
#usar criterion como 'gini' ou 'entropy' para testar se ha diferenca

text_clf = Pipeline([('vect', CountVectorizer()),('tfidf', TfidfTransformer()),
                     ('clf', DecisionTreeClassifier(random_state=0, criterion = 'entropy')),])


t0  = timeit.default_timer()
#treinar o modelo
text_clf.fit(lista_treinamento,targets_treinamento)
duration_train = timeit.default_timer() - t0 #tempo de treinamento em segundos
#testar o modelo
predicted = text_clf.predict(lista_teste)

#acuracia simples
#np.mean(predicted == targets_teste)

#metricas
print(metrics.classification_report(targets_teste, predicted, labels = [1,0],target_names = ['positivo', 'negativo']))


#testar feeature selection

#previsao para dados do crawler
#usou os seguintes comandos para ler os arquivos do crawler e classificar
#ler_data_teste(lista_teste_crawler)
#predicted = text_clf.predict(lista_teste_crawler)

#np.count_nonzero(predicted == 1) #calcula o numero de paginas positivas classificadas






