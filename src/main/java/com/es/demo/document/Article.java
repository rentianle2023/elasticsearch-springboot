package com.es.demo.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.Date;

import static com.es.demo.helper.Indices.ARTICLE_INDEX;

@Document(indexName = ARTICLE_INDEX,type = "_doc")
@Setting(settingPath = "static/es-settings.json")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    @Id
    @Field(type = FieldType.Keyword)
    public String id;

    @Field(type = FieldType.Text)
    public String title;

    @Field(type = FieldType.Text)
    public String body;

    @Field(type = FieldType.Date)
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date created;
}
