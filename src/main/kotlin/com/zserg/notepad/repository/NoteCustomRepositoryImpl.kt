package com.zserg.notepad.repository

import com.zserg.notepad.model.Note
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.*
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository
import java.time.LocalDateTime


@Repository
class NoteCustomRepositoryImpl : NoteCustomRepository {
    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    override fun findByParams(fromDate: LocalDateTime?, toDate: LocalDateTime?, title: String?, tags: List<String>): List<Note> {
        val query = Query()
        if(fromDate != null && toDate != null) {
            query.addCriteria(Criteria.where("createdAt").gte(fromDate).lte(toDate))
        }else {
            fromDate?.let { query.addCriteria(Criteria.where("createdAt").gte(it)) }
            toDate?.let { query.addCriteria(Criteria.where("createdAt").lte(it)) }
        }
        title?.let { query.addCriteria(Criteria.where("title").isEqualTo(it)) }
        if (tags.isNotEmpty()) {
            query.addCriteria(Criteria.where("tags").`in`(tags))
        }
        val result = mongoTemplate.find(query, Note::class.java)
        return result
    }

    override fun findRandomOne(tags: List<String>): List<Note> {
        val match: MatchOperation = Aggregation.match(Criteria.where("tags").`in`(tags))
        val sample: SampleOperation = Aggregation.sample(1)
        val aggregation: TypedAggregation<Note> = Aggregation.newAggregation(Note::class.java, match, sample)
        val result: AggregationResults<Note> = mongoTemplate.aggregate(aggregation, Note::class.java)
        return result.mappedResults
    }
}